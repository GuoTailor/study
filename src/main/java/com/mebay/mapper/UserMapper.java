package com.mebay.mapper;

import com.mebay.bean.Role;
import com.mebay.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface UserMapper {

    /**
     * 通过用户id获取用户
     *
     * @param id 用户id
     * @return user
     */
    User getUserById(@Param("id") Long id);

    /**
     * 通过单位id获取用户
     * 注意：该方法返回的user不具有角色信息
     * @param id 单位id
     * @return user列表
     */
    List<User> getUserByDeptId(@Param("deptId") Long id);

    /**
     * 通过很多单位id获取很多用户
     *
     * @param listId 单位id列表
     * @param search 分页查询参数 见{@link com.mebay.bean.PageQuery}.buildSubSql();
     * @return user列表
     */
    List<User> getUsersByDeptId(@Param("listId") List<Long> listId, @Param("search") String search);

    /**
     * 查询用户通过角色id和单位id
     * @param rid 角色id
     * @param did 单位id
     * @return 用户
     */
    List<User> getUserByRoleId(@Param("rid")Long rid, @Param("did")Long did);

    /**
     * 查询角色的权限
     * @param search 分页查询参数 见{@link com.mebay.bean.PageQuery}.buildSubSql()
     * @param did 单位id
     * @return 用户
     */
    List<User> getUsersByRole(@Param("search") String search, @Param("listId")List<Long> did);

    /**
     * 统计拥有该角的用户数量
     * @param rid 要统计的角色id
     * @return 数量
     */
    Long getUserCountByRoleId(@Param("rid")Long rid);

    /**
     * 新增一个user
     *
     * @param user 要增加的user
     * @return 受影响的行数 1：插入一行； 0：插入0行（插入失败）
     */
    int insert(User user);

    /**
     * 通过用户名获取user
     *
     * @param username 用户名
     * @return user
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * 通过用户id获取用户所拥有的角色
     *
     * @param id 用户id
     * @return 用户的角色列表
     */
    List<Role> getRolesByUserId(@Param("id") Long id);

    /**
     * 为用户添加多个角色
     *
     * @param id   用户id
     * @param rids 角色id列表
     * @return 受影响的行数 1：插入一行； 0：插入0行（插入失败）
     */
    int addRolesForUser(@Param("userid") Long id, @Param("rids") Collection<Long> rids);

    /**
     * 为用户添加多个角色
     *
     * @param id  用户id
     * @param rid 角色id
     * @return 受影响的行数 1：插入一行； 0：插入0行（插入失败）
     */
    int addRoleForUser(@Param("userid") Long id, @Param("rid") Long rid);

    /**
     * 为用户删除一个角色
     *
     * @param id   用户id
     * @param rids 角色id
     * @return 受影响的行数 1：删除一行； 0：删除0行（删除失败）
     */
    int deleteRoleForUser(@Param("userid") Long id, @Param("rid") Long rids);

    /**
     * 删除一个用户通过用户id
     *
     * @param id 用户id
     * @return 受影响的行数 1：删除一行； 0：删除0行（删除失败）
     */
    int deleteUser(@Param("id") Long id);

    /**
     * 修改用户的信息通过用户id
     *
     * @param id   用户id
     * @param user 用户的具体信息
     * @return 受影响的行数 1：修改一行； 0：修改0行（修改失败）
     */
    int updateUser(@Param("id") Long id, @Param("user") User user);
}
