package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.Constant;
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

import java.util.*;

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

    public User getUserById(Long id){
        User user = userMapper.getUserById(id);
        List<DeptTreeId> deptId = departmentService.getDeptIdTreeByUser();
        for (DeptTreeId d : deptId) {     //如果获取的用户不在自己管辖的范围内疚返回null
            if (d.findSubById(user.getDepId()) != null) {
                return user;
            }
        }
        return null;
    }

    public PageView<User> getAll(PageQuery pageQuery) {
        if(!UserUtils.isAdmin()) {
            return PageView.build(Collections.singletonList(userMapper.getUserById(UserUtils.getCurrentUser().getId())));
        }
        List<Long> ids = new LinkedList<>();
        departmentService.getDeptIdTreeByUser().forEach(d ->d.getIDs(ids));
        Page<User> page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        userMapper.getUsersByDeptId(ids, pageQuery.buildSubSql());
        return PageView.build(page);
    }

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

    public int register(User user) {
        if (userMapper.getUserByUsername(user.getUsername()) != null) {
            return -2;
        }

        if (user.getDepId() != null) {
            List<DeptTreeId> dept = departmentService.getDeptIdTreeByUser();
            for (DeptTreeId d : dept) {
                if (d.findSubById(user.getDepId()) == null) {
                    return -1;
                }
            }
        }
        user.setPassword(UserUtils.passwordEncoder(user.getPassword()));
        int i = userMapper.insert(user);
        //user.getRole().forEach(System.out::println);
        if (i == 1) {
            //TODO 添加权限验证
            /*Role role = roleMapper.findRolesByName("ROLE_STUDENT", null);
            userMapper.addRolesForUser(userMapper.getUserByUsername(user.getUsername()).getId().intValue(),
                    new Long[]{role.getId()});*/
            //roleMapper.
            return 1;
        }
        return 0;
    }

    public int update(User user, Long id) {     //TODO 判断自己能否修改
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
            }else
                user.setPassword(null);
            return userMapper.updateUser(id, user);
        }
        if (user.getPassword() != null && !user.getPassword().equals(""))
            user.setPassword(UserUtils.passwordEncoder(user.getPassword()));
        else user.setPassword(null);
        if(UserUtils.isAdmin()) {
            //user.setId(null);
            return userMapper.updateUser(id, user);
        }
        return -1;
    }

    public int addRole(Long uid, Long rid) {
        User present = userMapper.getUserById(UserUtils.getCurrentUser().getId());
        Role roles = roleMapper.findRolesById(present.getRole().stream().max(Comparator.comparing(Role::getId)).get().getId());
        List<Role> roleList = userMapper.getRolesByUserId(uid);
        if (roles.findSubById(rid) != null && !Util.hasAny((r, l) -> r.getId().equals(l), roleList, rid)) {     //TODO 提示重负添加
            return userMapper.addRoleForUser(uid, rid);
        }
        return -1;
    }


    public int deleteRole(Long uid, Long rid) {
        User user = userMapper.getUserById(uid);
        List<DeptTreeId> dept = departmentService.getDeptIdTreeByUser();
        for (DeptTreeId d : dept) {     //如果获取的用户不在自己管辖的范围内疚返回-1
            if (d.findSubById(user.getDepId()) != null) {
                List<Role> roleList = userMapper.getRolesByUserId(uid);
                if (Util.hasAny((r, l) -> r.getId().equals(l), roleList, rid)) //如果用户不具有该权限就返回-1
                    return userMapper.deleteRoleForUser(uid, rid);  //TODO 提示用户没有该权限
            }
        }
        return -1;
    }

    public int delete(Long id) {
        return userMapper.deleteUser(id);
    }

    /*public int updateRole(List<Role> rolesList) {
    //TODO 权限更新待做

    }*/

}
