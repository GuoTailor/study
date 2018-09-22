package com.study.controller;

import com.study.bean.RespBean;
import com.study.bean.User;
import com.study.mapper.UserMapper;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private final UserMapper userMapeer;
    private final UserService userService;

    @Autowired
    public HelloController(UserMapper userMapeer, UserService userService) {
        this.userMapeer = userMapeer;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Object login() {
        System.out.println("nmak");
        return "系统错乱";
    }

    @PostMapping("/register")
    public RespBean logon(User user) {
        System.out.println(user.toString());
        return userService.register(user);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userMapeer.getAll();
    }
}
