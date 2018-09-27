package com.mebay.mapper;

import com.mebay.bean.Role;
import com.mebay.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    User getUserById(@Param("id") int id);

    User getUserByToken(@Param("token") String token);

    List<User> getAll();

    int insert(User user);

    User getUserByUsername(@Param("username") String username);

    List<Role> getRolesByUserId(@Param("id") Long id);

    int addRolesForUser(@Param("userid") int id, @Param("rids") Long[] rids);

    int deleteUser(@Param("id") int id);
}