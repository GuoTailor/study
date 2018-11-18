package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.Constant;
import com.mebay.bean.*;
import com.mebay.common.FileUtil;
import com.mebay.common.UserUtils;
import com.mebay.common.Util;
import com.mebay.mapper.RoleMapper;
import com.mebay.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * user的数据库连接服务
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 通过id来获取用户
     * 如果获取的用户不在自己管辖的范围内就返回null
     *
     * @param id 用户id
     * @return 用户
     */
    public User getUserById(Long id) {
        User user = userMapper.getUserById(id);
        if (id.equals(UserUtils.getCurrentUser().getId())) {
            return user;
        }
        List<IdTree> deptId = departmentService.getDeptIdTreeByUser();
        for (IdTree d : deptId) {     //如果获取的用户不在自己管辖的范围内疚返回null
            if (d.findSubById(user.getDepId()) != null) {
                return user;
            }
        }
        return null;
    }

    public User getCurrentUser() {
        return userMapper.getUserById(UserUtils.getCurrentUser().getId());
    }

    /**
     * 获取自己能访问的所有用户<P>
     * 如果自己是管理员就返回自己单位下的所有用户，
     * 如果自己是超级管理员就放回所有用户，
     * 否则就放回自己
     *
     * @param pageQuery 分页参数
     * @return 用户列表
     */
    public PageView<User> getAll(PageQuery pageQuery) {
        if (!UserUtils.isAdmin()) {
            return PageView.build(Collections.singletonList(userMapper.getUserById(UserUtils.getCurrentUser().getId())));
        }
        List<Long> ids = new LinkedList<>();
        departmentService.getDeptIdTreeByUser().forEach(d -> d.getIDs(ids));
        Page<User> page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        userMapper.getUsersByDeptId(ids, pageQuery.buildSubSql());
        return PageView.build(page);
    }

    /**
     * 获取单位下的所有用户<p>
     * 注意：该方法不会鉴权
     *
     * @param id 单位id
     * @return 用户列表
     */
    public List<User> getUserByDepId(Long id) {
        return userMapper.getUsersByDeptId(Collections.singletonList(id), null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return user;
    }

    /**
     * 注册一个用户
     *
     * @param user 用户信息
     * @return 返会参数请看 {@link com.mebay.controller.UserController}.logon()
     */
    public int register(User user, List<Long> rid) {
        if (userMapper.getUserByUsername(user.getUsername()) != null) {
            return -2;
        }
        //用户密码必须为大写加小写字母加数字长度为6-16位
        if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\S]{6,16}$").matcher(user.getPassword()).matches()) {
            return -3;
        }
        if (user.getDepId() == null) {
            user.setDepId(UserUtils.getCurrentUser().getDepId());
        }
        List<IdTree> dept = departmentService.getDeptIdTreeByUser();
        for (IdTree d : dept) {
            if (d.findSubById(user.getDepId()) != null) {
                User present = userMapper.getUserById(UserUtils.getCurrentUser().getId());
                Role role = roleService.getAll();
                Long tier1 = Util.min(present.getRole(), (r, i) -> Math.min(role.getTier(r.getId()), i)).getId();
                Long tier2 = Util.min(rid, (r, i) -> Math.min(role.getTier(r), i));
                if (tier1 > tier2) {    //深度越深权限越小
                    return -4;
                }
                if (UserUtils.isAdmin(role.findSubById(tier2).getName())) {
                    if (!userMapper.getUserByRoleId(tier2, user.getDepId()).isEmpty())
                        return -5;
                }
                user.setPassword(UserUtils.passwordEncoder(user.getPassword()));
                if (userMapper.insert(user) == 1) {
                    Long id = userMapper.getUserByUsername(user.getUsername()).getId();
                    userMapper.addRolesForUser(id, rid);
                    return 1;
                }
                return 0;
            }
        }
        return -1;
    }

    /**
     * 更新，只能更新自己和自己单位下
     * @param user 更新信息
     * @param id 要更新的用户id
     */
    public int update(User user, Long id) {
        if (Util.isEmpty(user))
            return -2;
        user.setId(0L);
        if (UserUtils.getCurrentUser().getId().equals(id)) {   //如果修改的是自己
            user.setDepId(null);
            User present = userMapper.getUserById(UserUtils.getCurrentUser().getId());
            if (user.getOldPassword() != null && !StringUtils.isEmpty(user.getPassword())) {   //如果旧密码不为空
                if (new BCryptPasswordEncoder().matches(user.getOldPassword(), present.getPassword()))
                    user.setPassword(UserUtils.passwordEncoder(user.getPassword()));
                else
                    return -2;
            } else
                user.setPassword(null);
            String logoPath = present.getLogo();
            int i = userMapper.updateUser(id, user);
            if (i == 1 && logoPath != null && user.getLogo() != null && !logoPath.equals(user.getLogo())) {
                FileUtil.deleteFile("." + logoPath);
                System.out.println("删除" + logoPath);
            }
            return i;
        }
        if (user.getPassword() != null && !user.getPassword().equals(""))
            user.setPassword(UserUtils.passwordEncoder(user.getPassword()));
        else user.setPassword(null);
        List<IdTree> deptId = departmentService.getDeptIdTreeByUser();
        User u = userMapper.getUserById(id);
        if (!Util.hasAny((t, k) -> t.findSubById(k) != null, deptId, u.getDepId()))   //如果不在自己单位下就么有权限
            return -3;
        if (UserUtils.isAdmin()) {
            //user.setId(null);
            User present = userMapper.getUserById(id);
            String logoPath = present.getLogo();
            int i = userMapper.updateUser(id, user);
            if (i == 1 && logoPath != null && user.getLogo() != null && !logoPath.equals(user.getLogo())) {
                FileUtil.deleteFile("." + logoPath);
                System.out.println("删除" + logoPath);
            }
            return i;
        }
        return -1;
    }

    /**
     * 为用户添加一个角色，
     * 只能添加低于自己的角色
     *
     * @param uid 用户id
     * @param rid 角色id
     */
    @Deprecated
    public int addRole(Long uid, Long rid) {
        User present = userMapper.getUserById(UserUtils.getCurrentUser().getId());
        Role role = roleService.getAll();
        //最小的深度权限最大
        Role roles = roleService.findRolesById(Util.min(present.getRole(), (r, i) -> Math.min(role.getTier(r.getId()), i)).getId());
        List<Role> roleList = userMapper.getRolesByUserId(uid);
        //List<User> userList = userMapper.getUserByDeptId();
        if (roles.findSubById(rid) == null) {
            return -1;
        }
        if (Util.hasAny((r, l) -> r.getId().equals(l), roleList, rid)) {
            return -2;
        }
        //TODO 没有判断是否在自己管辖范围内
        return userMapper.addRoleForUser(uid, rid);
    }

    public int updateRole(long id, List<Long> roles) {
        User present = userMapper.getUserById(UserUtils.getCurrentUser().getId());
        User user = userMapper.getUserById(id);
        //用户拥有的权限
        List<Long> lodRole = user.getRole().stream().map(Role::getId).collect(Collectors.toList());
        Util.removeAllSame(lodRole, roles); //移除相同的
        List<IdTree> dept = departmentService.getDeptIdTreeByUser();
        for (IdTree d : dept) {     //如果获取的用户不在自己管辖的范围内疚返回-1
            if (d.findSubById(user.getDepId()) != null) {
                if (roles.size() > 0) {
                    Role allRole = roleService.getAll();
                    Long tier1 = Util.min(present.getRole(), (r, i) -> Math.min(allRole.getTier(r.getId()), i)).getId();
                    Long tier2 = Util.min(roles, (r, i) -> Math.min(allRole.getTier(r), i));
                    if (tier1 > tier2) {    //深度越深权限越小
                        return -1;
                    }
                    if (UserUtils.isAdmin(allRole.findSubById(tier2).getName())) {
                        if (!userMapper.getUserByRoleId(tier2, user.getDepId()).isEmpty())
                            return -3;  //已存在一个管理员
                    }
                    userMapper.addRolesForUser(id, roles);
                }
                for (Long rid : lodRole) {
                    userMapper.deleteRoleForUser(id, rid);
                }
                return 1;
            }
        }
        return -2;
    }

    /**
     * 为一个用户去除一个角色
     *
     * @param uid 用户id
     * @param rid 角色id
     */
    @Deprecated
    public int deleteRole(Long uid, Long rid) {
        User user = userMapper.getUserById(uid);
        List<IdTree> dept = departmentService.getDeptIdTreeByUser();
        for (IdTree d : dept) {     //如果获取的用户不在自己管辖的范围内疚返回-1
            if (d.findSubById(user.getDepId()) != null) {
                List<Role> roleList = userMapper.getRolesByUserId(uid);
                if (Util.hasAny((r, l) -> r.getId().equals(l), roleList, rid)) //如果用户不具有该权限就返回-1
                    return userMapper.deleteRoleForUser(uid, rid);
                else return -2;
            }
        }
        return -1;
    }

    /**
     * 删除，只能删除自己和自己单位下
     * @param id 要删除的用户id
     */
    public int delete(Long id) {
        User u = UserUtils.getCurrentUser();
        if (Util.hasAny((r, s) -> r.getName().equals(s),u.getRole(), "ROLE_SUPER_ADMIN")) {
            return userMapper.deleteUser(id);
        }
        User present = userMapper.getUserById(u.getId());
        if (UserUtils.isAdmin(Util.min(present.getRole(), (r, i) -> Math.min(r.getId().intValue(), i)).getName())) {
            List<IdTree> list = departmentService.getDeptsByCreationId(id);
            if (list != null && !list.isEmpty()) {
                return -2;
            }
        }
        if (u.getId().equals(id)) {   //如果删除的是自己
            return userMapper.deleteUser(id);
        }
        List<IdTree> deptId = departmentService.getDeptIdTreeByUser();
        if (Util.hasAny((t, k) -> t.findSubById(k) != null, deptId, present.getDepId()))   //如果在自己单位下
            return userMapper.deleteUser(id);
        return -1;
    }

    public Long getUserCountByRoleId(Long rid) {
        return userMapper.getUserCountByRoleId(rid);
    }

}
