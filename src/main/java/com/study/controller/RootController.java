package com.study.controller;

import com.study.Constant;
import com.study.bean.Department;
import com.study.bean.RespBean;
import com.study.bean.Role;
import com.study.service.DepartmentService;
import com.study.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/root")
public class RootController {

    final private RoleService roleService;

    @Autowired
    public RootController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(value = "/role")
    public RespBean addRole(Role role) {
        return roleService.addNewRole(role.getName(), role.getNameZh());
    }

    @DeleteMapping(value = "/role/{id}")
    public RespBean deleteRole(@PathVariable Long id) {
        if (roleService.deleteRoleById(id) == 1)
            return new RespBean("200", "成功");
        else return new RespBean(Constant.NotModified, "删除失败资源未找到");
    }

    @PutMapping(value = "/role")
    public RespBean updateRole(Role role){
        if (roleService.updateRole(role))
            return new RespBean("200", "成功");
        else return new RespBean(Constant.NotModified, "更新失败!资源未找到");
    }

    @GetMapping(value = "/role")
    public List<Role> getAll(){
        return roleService.roles();
    }

}
