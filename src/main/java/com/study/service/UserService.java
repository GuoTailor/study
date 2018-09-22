package com.study.service;

import com.study.bean.RespBean;
import com.study.bean.Role;
import com.study.mapper.RoleMapper;
import com.study.mapper.UserMapper;
import com.study.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Autowired
    public UserService(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
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

    public RespBean register(User user) {
        if (userMapper.getUserByUsername(user.getUsername()) != null) {
            return new RespBean("error", "用户名重复，注册失败!");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        int i = userMapper.insert(user);
        if (i == 1) {
            Role role = roleMapper.findRolesByName("ROLE_STUDENT", null);
            userMapper.addRolesForUser(userMapper.getUserByUsername(user.getUsername()).getId().intValue(),
                    new Long[]{role.getId()});
            //roleMapper.
            return new RespBean("success", "注册成功!");
        }
        return new RespBean("error", "注册失败!");
    }

}
