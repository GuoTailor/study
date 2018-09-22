package com.study.service;

import com.study.Constant;
import com.study.bean.RespBean;
import com.study.bean.Role;
import com.study.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> roles() {
        return roleMapper.findRolesAll();
    }

    public RespBean addNewRole(String role, String roleZh) {
        if (role == null || "".equals(role))
            return new RespBean(Constant.RESCODE_EXCEPTION_DATA, "英文名不能为空");
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        if (roleMapper.findRolesByName(role, roleZh) != null)
            return new RespBean(Constant.RESCODE_CONFICT, "英文或中文名已存在");
        if (roleMapper.addNewRole(role, roleZh))
            return new RespBean("200", "succeed");
        return new RespBean("error", "未知错误");

    }

    public int deleteRoleById(Long rid) {
        return roleMapper.deleteRoleById(rid);
    }

    public boolean updateRole(Role role) {
        return roleMapper.updateRole(role);
    }
}
