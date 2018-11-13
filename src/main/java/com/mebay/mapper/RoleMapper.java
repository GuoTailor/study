package com.mebay.mapper;

import com.mebay.bean.IdTree;
import com.mebay.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 角色的Mapper
 */
public interface RoleMapper {
    /**
     * 通过角色id获取Role <P>
     * 该方法将会返回它的子角色
     *
     * @param id 角色id
     * @return role
     */
    Role findRolesById(@Param("id") Long id);

    /**
     * 获取所有的角色
     *
     * @param search 分页查询参数 见{@link com.mebay.bean.PageQuery}.buildSubSql();
     * @return role列表
     */
    List<Role> findAllRole(@Param("search") String search);

    IdTree getRoleIdTreeById(@Param("id") Long id);

    /**
     * 获取角色通过角色的名字（中文或英文）
     *
     * @param name   英文名字
     * @param nameZh 中文名字
     * @return role
     */
    Role findRolesByName(@Param("name") String name, @Param("nameZh") String nameZh);

    /**
     * 通过角色id获取Role <P>
     *
     * @param id 角色id
     * @return role
     */
    Role findRoleById(@Param("id") Long id);

    /**
     * 获取角色通过id列表
     *
     * @param id     id列表
     * @param search 分页参数{@link com.mebay.bean.PageQuery}.buildSubSql();
     * @return role列表
     */
    List<Role> getRoleByIds(@Param("ids") List<Long> id, @Param("search") String search);

    /**
     * 通过角色的父本id获取role
     * 该方法将返回它的子角色
     *
     * @param pid 父角色id
     * @return role
     */
    Role findRoleByPid(@Param("pid") Long pid);

    /**
     * 添加一个新的角色
     *
     * @param role   角色的信息
     * @return 受影响的行数1：插入一行； 0：插入0行（插入失败）
     */
    int addNewRole(Role role);

    /**
     * 删除一个角色通过角色的id
     *
     * @param rid 角色id
     * @return 受影响的行数 1：删除一行； 0：删除0行（删除失败）
     */
    int deleteRoleById(Long rid);

    /**
     * 更新角色信息通过角色id
     *
     * @param role 新的角色信息
     * @param id   要修改的角色的id
     * @return 受影响的行数 1：修改一行； 0：修改0行（修改失败）
     */
    int updateRole(Role role, @Param("id") Long id);

    /**
     * 为角色添加具体能访问的url菜单
     *
     * @param rid  角色id
     * @param mids url菜单的id列表
     */
    void addMenuToRole(@Param("rid") Long rid, @Param("mids") Collection<Long> mids);

    /**
     * 移除角色能访问的具体菜单
     *
     * @param rid  角色id
     * @param mids 要移除的菜单id集合
     */
    void removeMenuByRole(@Param("rid") Long rid, @Param("mids") Collection<Long> mids);

    /**
     * 为角色添加具体能访问的url菜单的访问方法
     *
     * @param rid     角色id
     * @param menuIds url菜单的访问方法map 键为url的id 值为（GET，POST，DELETE，PUT）
     */
    void addMenuMethodToRole(@Param("rid") Long rid, @Param("menuIds") Map<Long, List<String>> menuIds);

    /**
     * 移除角色能访问的具体url菜单的访问方法
     *
     * @param rid     角色id
     * @param menuIds 要移除的url菜单的访问方法map 键为url的id 值为（GET，POST，DELETE，PUT）
     */
    void removeMenuMethodByRole(@Param("rid") Long rid, @Param("menuIds") Map<Long, List<String>> menuIds);
}

