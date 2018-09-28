package com.mebay.service;

import com.mebay.Constant;
import com.mebay.bean.RespBean;
import com.mebay.bean.Role;
import com.mebay.mapper.RoleMapper;
import com.mebay.mapper.UserMapper;
import com.mebay.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public User getUserById(Long id){
        return userMapper.getUserById(id);
    }

    public List<User> getAll() {
        return userMapper.getAll();
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

    public RespBean<String> register(User user) {
        if (userMapper.getUserByUsername(user.getUsername()) != null) {
            return new RespBean<>(Constant.NotModified, "用户名重复，注册失败!");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        int i = userMapper.insert(user);
        if (i == 1) {
            Role role = roleMapper.findRolesByName("ROLE_STUDENT", null);
            userMapper.addRolesForUser(userMapper.getUserByUsername(user.getUsername()).getId().intValue(),
                    new Long[]{role.getId()});
            //roleMapper.
            return new RespBean<>(Constant.RESCODE_SUCCESS, "注册成功!");
        }
        return new RespBean<>(Constant.RESCODE_EXCEPTION_DATA, "注册失败!");
    }

}
