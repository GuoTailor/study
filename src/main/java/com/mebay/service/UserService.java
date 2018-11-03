package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.bean.*;
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
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * user的数据库连接服务
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    public UserService(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
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
        List<DeptTreeId> deptId = departmentService.getDeptIdTreeByUser();
        for (DeptTreeId d : deptId) {     //如果获取的用户不在自己管辖的范围内疚返回null
            if (d.findSubById(user.getDepId()) != null) {
                return user;
            }
        }
        return null;
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
    public int register(User user) {
        if (userMapper.getUserByUsername(user.getUsername()) != null) {
            return -2;
        }
        //用户密码必须为大写加小写字母加数字长度为6-16位
        if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\S]{6,16}$").matcher(user.getPassword()).matches()) {
            return -3;
        }
        if (user.getDepId() != null) {
            List<DeptTreeId> dept = departmentService.getDeptIdTreeByUser();
            for (DeptTreeId d : dept) {
                if (d.findSubById(user.getDepId()) == null) {
                    return -1;
                }
            }
        } else {
            user.setDepId(UserUtils.getCurrentUser().getDepId());
        }
        user.setPassword(UserUtils.passwordEncoder(user.getPassword()));
        return userMapper.insert(user);
    }

    /**
     * 更新，只能更新自己和自己单位下
     * @param user 更新信息
     * @param id 要更新的用户id
     */
    public int update(User user, Long id) {
        if (Util.isEmpty(user))
            return -2;
        User present = userMapper.getUserById(UserUtils.getCurrentUser().getId());
        user.setId(0L);
        if (present.getId().equals(id)) {   //如果修改的是自己
            user.setDepId(null);
            if (user.getOldPassword() != null && !StringUtils.isEmpty(user.getPassword())) {   //如果旧密码不为空
                if (new BCryptPasswordEncoder().matches(user.getOldPassword(), present.getPassword()))
                    user.setPassword(UserUtils.passwordEncoder(user.getPassword()));
                else
                    return -2;
            } else
                user.setPassword(null);
            return userMapper.updateUser(id, user);
        }
        if (user.getPassword() != null && !user.getPassword().equals(""))
            user.setPassword(UserUtils.passwordEncoder(user.getPassword()));
        else user.setPassword(null);
        List<DeptTreeId> deptId = departmentService.getDeptIdTreeByUser();
        if (!Util.hasAny((t, k) -> t.findSubById(id) != null, deptId, id))   //如果不在自己单位下就么有权限
            return -3;
        if (UserUtils.isAdmin()) {
            //user.setId(null);
            return userMapper.updateUser(id, user);
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
    public int addRole(Long uid, Long rid) {
        User present = userMapper.getUserById(UserUtils.getCurrentUser().getId());
        Role roles = roleMapper.findRolesById(present.getRole().stream().max(Comparator.comparing(Role::getId)).get().getId());
        List<Role> roleList = userMapper.getRolesByUserId(uid);
        if (roles.findSubById(rid) == null) {
            return -1;
        }
        if (Util.hasAny((r, l) -> r.getId().equals(l), roleList, rid)) {
            return -2;
        }
        return userMapper.addRoleForUser(uid, rid);
    }

    /**
     * 为一个用户去除一个角色
     *
     * @param uid 用户id
     * @param rid 角色id
     */
    public int deleteRole(Long uid, Long rid) {
        User user = userMapper.getUserById(uid);
        List<DeptTreeId> dept = departmentService.getDeptIdTreeByUser();
        for (DeptTreeId d : dept) {     //如果获取的用户不在自己管辖的范围内疚返回-1
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
        if (UserUtils.getCurrentUser().getId().equals(id)) {   //如果修改的是自己
            return userMapper.deleteUser(id);
        }
        List<DeptTreeId> deptId = departmentService.getDeptIdTreeByUser();
        if (Util.hasAny((t, k) -> t.findSubById(id) != null, deptId, id))   //如果在自己单位下
            return userMapper.deleteUser(id);
        return -1;
    }

}
