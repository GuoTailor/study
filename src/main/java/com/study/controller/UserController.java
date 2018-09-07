package com.study.controller;

import com.study.bean.User;
import com.study.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/getUser")
    public User getUserById(@RequestParam(value = "id", defaultValue = "0") int id) {
        return userMapper.getUserById(id);
    }
}
