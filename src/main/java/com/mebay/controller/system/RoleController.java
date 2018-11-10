package com.mebay.controller.system;

import com.mebay.Constant;
import com.mebay.bean.*;
import com.mebay.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
@Api(tags = "系统角色")
public class RoleController {
    final private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(value = "/role")
    @ApiOperation(value = "添加一个角色")
    @ApiImplicitParam(paramType = "query", name = "nameZh", value = "角色中文名称（角色描述）", required = true, dataType = "String")
    public RespBody<String> addRole(Role role) {
        RespBody<String> respBody = new RespBody<>(roleService.addNewRole(role.getName(), role.getNameZh()));
        respBody.put(-1, "英文名不能为空")
                .put(-2, "英文或中文名已存在")
                .put(1, "添加成功")
                .put(0, "添加失败");
        return respBody;
    }

    @DeleteMapping(value = "/role/{id}")
    @ApiOperation(value = "删除一个角色")
    public RespBody<String> deleteRole(@PathVariable Long id) {
        RespBody<String> respBody = new RespBody<>(roleService.deleteRoleById(id));
        respBody.put(1, "成功").put(0, "删除失败!资源未找到");
        return respBody;
    }

    @PutMapping(value = "/role/{PutId}")
    @ApiOperation(value = "更新角色信息")
    @ApiImplicitParam(paramType = "query", name = "name", value = "角色名", dataType = "String")
    public RespBody<String> updateRole(Role role, @PathVariable Long PutId) {
        RespBody<String> respBody = new RespBody<>(roleService.updateRole(role, PutId));
        respBody.put(1, "成功").put(0, "更新失败!资源未找到").put(-1, "更新失败!请至少更新一个属性（字段）");
        return respBody;
    }

    @GetMapping(value = "/role")
    @ApiOperation(value = "获取该角色能访问的角色列表")
    public RespBody<List<Role>> getAll() {
        return new RespBody<>(1, roleService.roles());
    }

    @PutMapping("/role/assignment/{id}")
    @ApiOperation(value = "为指定权限添加具体能访问的路径", notes = "权限点自定义")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "角色id", required = true, dataType = "Long")
    })
    public RespBody<String> add(@PathVariable Long id, @RequestBody Map<Long, List<String>> params) {
        roleService.update(params, id);
        return new RespBody<>(Constant.RESCODE_SUCCESS, "成功");
    }

    @GetMapping("/role/assignment/{id}")
    @ApiOperation(value = "获取角色的具体权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "角色id", required = true, dataType = "Long")
    })
    public RespBody<List<Menu>> get(@PathVariable Long id) {
        return new RespBody<>(1, roleService.getMenus(id));
    }

}
