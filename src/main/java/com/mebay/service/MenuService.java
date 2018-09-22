package com.mebay.service;

import com.mebay.bean.Menu;
import com.mebay.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {
    @Autowired
    MenuMapper mapper;

    public Menu getMenusByUserId(Long hrId) {
        List<Menu> menus = mapper.getAllMenu();
        List<Long> mid = mapper.getMenuIdByUserId(hrId);
        Menu m = getChild(1L, menus);
        empty(m, mid);
        return m;
    }

    public List<Menu> getAllMenu() {
        return mapper.getAllMenu();
    }

    /**
     * 去掉树中没用到的节点
     *
     * @param menus menu树
     * @param mid   没用的节点集合
     * @return menu树
     */
    boolean empty(Menu menus, List<Long> mid) {
        List<Menu> menuList = menus.getChildren();
        AtomicBoolean flag = new AtomicBoolean(false);
        if (menuList.isEmpty()) {
            return mid.contains(menus.getId());
        }
        menus.setChildren(menuList.parallelStream().filter(m -> {
            if (empty(m, mid))
                flag.set(true);
            return empty(m, mid);
        }).collect(Collectors.toList()));
        return flag.get();
    }

    /**
     * 把菜单列表建立成一颗树
     *
     * @param id       树的根id
     * @param menuList 树的各个节点
     * @return menu树
     */
    Menu getChild(Long id, List<Menu> menuList) {
        Menu menus = new Menu();
        for (Menu menu : menuList) {
            if (menu.getId().equals(id))
                menus = menu;
        }
        for (Menu menu : menuList) {
            System.out.println(menu.getParentId() + " " + menu.getId());
            if (menu.getId().equals(1L))
                continue;
            if (menu.getParentId().equals(menus.getId())) {
                menus.getChildren().add(getChild(menu.getId(), menuList));
            }
        }
        return menus;
    }
}
