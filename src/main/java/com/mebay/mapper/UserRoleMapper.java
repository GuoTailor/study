package com.mebay.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 无用内容  可删除
 */
public interface UserRoleMapper {

    boolean addUserToRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    boolean deleteRoleByUserId(@Param("userId") Long userId);
}
