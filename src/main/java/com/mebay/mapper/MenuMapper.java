package com.mebay.mapper;

import com.mebay.bean.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    /**
     * 获取所有的菜单和它的访问方法
     * @param rid 菜单的id 如果为null就默认为获取所有菜单
     * @return 菜单列表
     */
    List<Menu> getAllMenu(@Param("rid") Long rid);

    //List<Menu> getMenusByUserId(Long hrId);

    /**
     * 获取用户能访问的菜单id
     * @param id 用户id
     * @return 菜单id列表
     */
    List<Long> getMenuIdByUserId(@Param("id") Long id);

    /**
     * 该方法已弃用
     * @return
     */
    List<Menu> menuTree();

    /**
     * 获取角色能访问的菜单的id
     * @param rid 角色id
     * @return 菜单id列表
     */
    List<Long> getMenusByRoleId(@Param("rid") Long rid);

    /**
     * 获取菜单通过菜单id
     * 该方法将放回菜单的子菜单
     * @param id 菜单id
     * @return 菜单
     */
    Menu getMenusById(@Param("id") Long id);
}
