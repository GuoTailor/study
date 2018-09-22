package com.mebay.mapper;

import com.mebay.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    List<Role> findRolesAll();

    Role findRolesByName(@Param("name") String name, @Param("nameZh") String nameZh);

    boolean addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    int deleteRoleById(Long rid);

    boolean updateRole(Role role);
}

