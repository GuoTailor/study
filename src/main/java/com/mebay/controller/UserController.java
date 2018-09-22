package com.mebay.controller;

import com.mebay.bean.User;
import com.mebay.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/getUser")
    public User getUserById(@RequestParam(value = "id", defaultValue = "0") int id) {
        return userMapper.getUserById(id);
    }
}
