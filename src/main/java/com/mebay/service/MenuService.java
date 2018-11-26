package com.mebay.service;

import com.mebay.Constant;
import com.mebay.bean.Menu;
import com.mebay.bean.Role;
import com.mebay.bean.User;
import com.mebay.common.UserUtils;
import com.mebay.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 菜单表
 */

@Service
@Transactional
public class MenuService {
    private final MenuMapper mapper;

    @Autowired
    public MenuService(MenuMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 获取菜单通过当前用户
     * @return 菜单
     */
    public Menu getMenusByUserId() {
        User user = UserUtils.getCurrentUser();
        if (user.getId() == null){
            return null;
        }
        ArrayList<Menu> menus = (ArrayList<Menu>) Constant.map.get("menu");
        if (menus == null) {
            menus = (ArrayList<Menu>) mapper.getAllMenu(null);
            Constant.map.put("menu", new ArrayList<>(menus));
        }
        List<Long> mid = mapper.getMenuIdByUserId(user.getId());
        Menu m = getChild(1L, menus);
        System.out.println(menus.size());
        System.out.println(m.getChildren().size());
        empty(m, mid, user.getRole());
        return m;
    }

    public List<Menu> getMenus() {
        User user = UserUtils.getCurrentUser();
        if (user.getId() == null){
            return null;
        }
        return mapper.getAllMenu(user.getId());
    }

    public List<Menu> getMenusByRole(Role role) {
        List<Menu> menus = mapper.getAllMenu(role.getId());
        List<Long> mid = mapper.getMenusByRoleId(role.getId());
        return menus.stream().filter(m -> mid.contains(m.getId())).peek(
                m -> m.setRoles(m.getRoles().stream().filter(r -> r.getName().equals(role.getName()))
                        .limit(1).collect(Collectors.toList()))).collect(Collectors.toList());
    }

    /**
     * 获取全部的菜单，包括菜单的全部所需权限
     *
     * @return 菜单列表
     */
    public List<Menu> getAllMenu() {
        return mapper.getAllMenu(null);
    }

    /**
     * 去掉树中没用到的节点,并去掉没有的权限
     *
     * @param menus menu树
     * @param mid   用户的节点集合
     * @param role 用户的角色集合
     * @return menu树
     */
    boolean empty(Menu menus, List<Long> mid, List<Role> role) {
        if (role.contains(new Role("ROLE_SUPER_ADMIN")))
            return false;
        List<Menu> menuList = menus.getChildren();
        AtomicBoolean flag = new AtomicBoolean(false);  //子节点是否有用标志位，如无用将删除该节点
        menus.setRoles(menus.getRoles().stream().filter(role::contains).limit(role.size()).collect(Collectors.toList()));
        if (menuList.isEmpty()) {
            return mid.contains(menus.getId());
        }
        menus.setChildren(menuList.parallelStream().filter(m -> {
            if (empty(m, mid, role)) {
                flag.set(true);
                return true;
            }
            return false;
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
        Menu menus = null;
        for (Menu menu : menuList) {
            if (menu.getId().equals(id)) {
                menus = (Menu) menu.clone();
            }
        }
        if (menus == null) {
            return null;
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

    List<Menu> buildTree(List<Menu> menus) {
        ArrayList<Menu> menuList = new ArrayList<>(menus.size());
        for (Menu menu : menus) {
            if (menuList.contains(menu)) {
                menuList.get(menuList.indexOf(menu)).getChildren().add(menu);
            }else {
                menuList.add(menu);
            }
        }
        return menuList;
    }
}
