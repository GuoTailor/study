package com.study.mapper;

import com.study.bean.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    List<Menu> getAllMenu();

    //List<Menu> getMenusByUserId(Long hrId);

    List<Long> getMenuIdByUserId(@Param("id") Long id);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
