package com.mebay.controller;

import com.mebay.bean.RespBean;
import com.mebay.bean.User;
import com.mebay.mapper.UserMapper;
import com.mebay.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO ----------------该类存在权限逻辑问题------------------
@RestController
@RequestMapping("/users")
@Api(tags = "关于用户", value = "无需权限的接口")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponse(code = 200, message = "成功", response = RespBean.class)
    @ApiOperation(value = "获取用户信息 根据用户id", notes = "并不是所有用户都能获取，有权限")
    @ApiImplicitParam(paramType = "query", name = "id", value = "用户Id", required = true, dataType = "Long")
    @GetMapping("/getUser")
    public RespBean<User> getUserById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return new RespBean<>(200, userService.getUserById(id));
    }

    @ApiOperation(value = "注册用户")
    @PostMapping("/register")
    public RespBean<String> logon(User user) {
        System.out.println(user.toString());
        return userService.register(user);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }
}
