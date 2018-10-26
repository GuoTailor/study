package com.mebay.mapper;

import com.mebay.bean.Role;
import com.mebay.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    User getUserById(@Param("id") Long id);

    User getUserByToken(@Param("token") String token);

    User getUserByDeptId(@Param("deptId")Long id);

    List<User> getUsersByDeptId(@Param("listId") List<Long> listId, @Param("search") String search);

    int insert(User user);

    User getUserByUsername(@Param("username") String username);

    List<Role> getRolesByUserId(@Param("id") Long id);

    int addRolesForUser(@Param("userid") Long id, @Param("rids") Long[] rids);

    int addRoleForUser(@Param("userid") Long id, @Param("rid") Long rid);

    int deleteRoleForUser(@Param("userid") Long id, @Param("rid") Long rids);

    int deleteUser(@Param("id") Long id);

    int updateUser(@Param("id") Long id, @Param("user") User user);
}
