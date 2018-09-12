package com.study.controller;

import com.study.bean.Department;
import com.study.bean.RespBean;
import com.study.bean.Role;
import com.study.mapper.DepartmentMapper;
import com.study.mapper.RoleMapper;
import com.study.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {

    final private DepartmentMapper departmentMapper;
    final private RoleService roleService;

    @Autowired
    public SystemController(DepartmentMapper departmentMapper, RoleService roleService) {
        this.departmentMapper = departmentMapper;
        this.roleService = roleService;
    }

    @Transactional(timeout=36000,rollbackFor=Exception.class)
    @PostMapping(value = "/dep")
    public Map<String, Object> addDep(Department department) {
        department.setEnabled(true);
        Map<String, Object> map = new HashMap<>();
        if (department.getParentId() >= 1 && departmentMapper.existParentId(department.getParentId())) {
            if (departmentMapper.addDep(department) == 1) {
                departmentMapper.setParentById(department.getParentId(), true);
                map.put("status", "success");
                map.put("message", department);
            }else {
                map.put("status", "error");
                map.put("message", "添加失败!");
            }
        }
        map.put("status", "error");
        map.put("message", "添加失败!或父节点不存在");
        return map;
    }

    @DeleteMapping(value = "/dep/{did}")
    public RespBean deleteDep(@PathVariable Long did) {
        if (departmentMapper.deleteDep(did) == 1) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @GetMapping(value = "/dep/{pid}")
    public List<Department> getDepByPid(@PathVariable Long pid) {
        return departmentMapper.getDepByPid(pid);
    }

    @GetMapping(value = "/dep")
    public List<Department> getAllDeps() {
        return departmentMapper.getAllDeps();
    }

    @PostMapping(value = "/role")
    public RespBean addRole(Role role) {
        return roleService.addNewRole(role.getName(), role.getNameZh());
    }

    @DeleteMapping(value = "/role/{id}")
    public RespBean deleteRole(@PathVariable Long id) {
        if (roleService.deleteRoleById(id) == 1)
            return new RespBean("200", "成功");
        else return new RespBean("error", "删除失败");
    }

    @PostMapping(value = "/role")
    public RespBean updateRole(Role role){
        if (roleService.updateRole(role))
            return new RespBean("200", "成功");
        else return new RespBean("error", "更新失败");
    }

    @GetMapping(value = "/role")
    public List<Role> getAll(){
        return roleService.roles();
    }

}
