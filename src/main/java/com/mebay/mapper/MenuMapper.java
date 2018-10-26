package com.mebay.mapper;

import com.mebay.bean.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    List<Menu> getAllMenu(@Param("rid") Long rid);

    //List<Menu> getMenusByUserId(Long hrId);

    List<Long> getMenuIdByUserId(@Param("id") Long id);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);

    List<Long> getMenusByRoleId(@Param("rid") Long rid);
}
