package com.mebay.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {

    boolean addUserToRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    boolean deleteRoleByUserId(@Param("userId") Long userId);
}
