package com.mebay.mapper;

import com.mebay.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RoleMapper {
    Role findRolesById(@Param("id") Long id);

    List<Role> findAllRole(@Param("search") String search);

    Role findRolesByName(@Param("name") String name, @Param("nameZh") String nameZh);

    Role findRoleById(@Param("id") Long id);

    Role findRoleByPid(@Param("pid")Long pid);

    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    int deleteRoleById(Long rid);

    int updateRole(Role role, @Param("id") Long id);

    void addMenuToRole(@Param("rid") Long rid, @Param("mids") Collection<Long> mids);

    void removeMenuByRole(@Param("rid") Long rid, @Param("mids") Collection<Long> mids);

    void addMenuMethodToRole(@Param("rid") Long rid, @Param("menuIds") Map<Long, List<String>> menuIds);

    void removeMenuMethodByRole(@Param("rid") Long rid, @Param("menuIds") Map<Long, List<String>> menuIds);
}

