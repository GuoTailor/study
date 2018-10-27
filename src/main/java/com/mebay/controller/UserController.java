package com.mebay.controller;

import com.mebay.bean.*;
import com.mebay.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponse(code = 200, message = "成功", response = RespBody.class)
    @ApiOperation(value = "获取用户信息 根据用户id", notes = "并不是所有用户都能获取，有权限")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户Id", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public RespBody getUserById(@PathVariable(value = "id") Long id) {
        User user = userService.getUserById(id);
        return new RespBody<>(user == null ? 0 : 1)
                .put(0, "用户不存在")
                .put(1, user);
    }

    @ApiOperation(value = "注册用户", notes = "权限存在问题，关于权限字段不用传")
    @PostMapping()
    public RespBody<String> logon(@ModelAttribute User user) {
        System.out.println(user.toString());
        return new RespBody<String>(userService.register(user))
                .put(-2, "注册失败!用户名重复")
                .put(-1, "该用户不能在该单位下，注册失败")
                .put(1, "注册成功!")
                .put(0, "系统异常!请联系管理员")
                .put(-3, "注册失败!密码必须为大写字母+小写字母+数字");
    }

    @ApiOperation(value = "获取当前用户所属节点下的所有用户", notes = "如果自己也在该单位下那么也能查出自己，如果用户不能查看其他人账户将只返回他自己")
    @GetMapping()
    public RespBody<PageView<User>> getAll(PageQuery pageQuery) {
        return new RespBody<>(1, userService.getAll(pageQuery));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新用户资料(可以更新自己的资料也可以更新下属的资料)", notes = "也可以更改自己的资料\n权限存在问题，关于权限字段不用传")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true, dataType = "Long"),
    })
    public RespBody<String> updateUserRole(@ModelAttribute User user, @PathVariable Long id) {
        return new RespBody<String>(userService.update(user, id))
                .put(1, "更新成功")
                .put(-1, "没有该用户，或没有该用户的操作权限")
                .put(0, "更新失败，未知错误")
                .put(-2, "更新失败!请至少更新一个属性（字段）");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "通过用户id删除用户")
    public RespBody<String> delete(@PathVariable(value = "id") Long id) {
        return new RespBody<String>(userService.delete(id))
                .put(-1, "没有该用户，或没有该用户的操作权限")
                .put(1,"删除成功")
                .put(0, "删除失败，未知错误");
    }

    @PostMapping("/role/{id}")
    @ApiOperation(value = "为用户添加一个角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "rid", value = "角色id", required = true, dataType = "Long"),
    })
    public RespBody<String> addRole(@PathVariable Long id, @RequestParam Long rid) {
        return new RespBody<String>(userService.addRole(id, rid))
                .put(-1, "没有该用户或角色，或没有该用户的操作权限")
                .put(1,"添加成功")
                .put(0, "添加失败，未知错误");
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "为用户删除一个角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "rid", value = "角色id", required = true, dataType = "Long"),
    })
    public RespBody<String> deleteRole(@PathVariable Long id, @RequestParam Long rid) {
        return new RespBody<String>(userService.deleteRole(id, rid))
                .put(-1, "没有该用户或角色，或没有该用户的操作权限")
                .put(1,"删除成功")
                .put(0, "删除失败，未知错误");
    }
}
