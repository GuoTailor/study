package com.mebay.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "用来测试的接口一般不用权限",tags = "无需权限的接口", description = "无需jwt验证")
public class HelloController {

    @ApiOperation(value = "登陆", notes = "测试账号：test，密码：admin\n")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String")
    })
    @ApiResponse(code = 200, message = "成功")
    @PostMapping("/login")
    public Object login() {
        System.out.println("nmak");
        return "系统错乱";
    }

}
