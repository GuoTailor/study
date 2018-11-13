package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.Constant;
import com.mebay.bean.*;
import com.mebay.common.UserUtils;
import com.mebay.common.Util;
import com.mebay.config.UrlFilterInvocationSecurityMetadataSource;
import com.mebay.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor={Exception.class})
public class RoleService {

    private final RoleMapper roleMapper;
    private final MenuService menuService;
    @Autowired
    private UserService userService;

    @Autowired
    public RoleService(RoleMapper roleMapper, MenuService menuService) {
        this.roleMapper = roleMapper;
        this.menuService = menuService;
    }

    /**
     * 获取该角色能访问的角色列表
     * @return 分页之后的角色列表
     */
    public List<Role> roles() {
        Long id = userService.getCurrentUser().getRole().stream().max(Comparator.comparing(Role::getId)).get().getId();
        IdTree idTree = roleMapper.getRoleIdTreeById(id);
        List<Long> ids = new ArrayList<>();
        idTree.getIDs(ids);
        return roleMapper.getRoleByIds(ids, null);
    }

    public Role getRolesById(Long id) {
        return roleMapper.findRolesById(id);
    }

    public Role findRolesById(Long id) {
        return roleMapper.findRolesById(id);
    }

    public Role getAll() {
        Role role = (Role) Constant.map.get("Role");
        if (role == null) {
            role = roleMapper.findRolesById(1L);
            Constant.map.put("Role", role);
        }
        return role;
    }

    /**
     * 增加一个角色
     * @param role 角色
     */
    public int addNewRole(Role role) {
        if (role.getName() == null || "".equals(role.getName()))
            return -1;
        if (role.getPid() == null) {
            return -3;
        }
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (roleMapper.findRolesByName(role.getName(), role.getNameZh()) != null)
            return -2;
        return roleMapper.addNewRole(role);
    }

    public Role findRolesByName(String role, String roleZh) {
        return roleMapper.findRolesByName(role, roleZh);
    }

    public int deleteRoleById(Long rid) {
        if (userService.getUserCountByRoleId(rid) != 0) {
            return -1;
        }
        return roleMapper.deleteRoleById(rid);
    }

    public int updateRole(Role role, Long id) {
        if (Util.isEmpty(role))
            return -1;
        return roleMapper.updateRole(role, id);
    }

    /**
     * 更新角色的具体能访问的url路径和方法
     * @param menuRoles
     * @param id 角色id
     */
    public void update(Map<Long, List<String>> menuRoles, Long id) {
        List<Menu> menus = menuService.getMenusByRole(roleMapper.findRoleById(id));
        Map<Long, List<String>> remove = menus.stream().collect(Collectors.toMap(Menu::getId, ms -> ms.getRoles().get(0).getMethod()));
        for (Map.Entry<Long, List<String>> entry : menuRoles.entrySet()) {
            List<String> roleList;
            if ((roleList = remove.get(entry.getKey())) != null) {  //在已有的权限中找请求的菜单id
                List<String> strings = entry.getValue();
                Util.removeAllSame(roleList, strings);//去掉相同的
            }
        }
        removeMenuMethodByRole(id, remove);     //移除没有的菜单访问方法
        addMenuMethodToRole(id, menuRoles);     //添加新的菜单访问方法
        Set<Long> removeSet = remove.keySet();
        Set<Long> addSet = menuRoles.keySet();
        Util.removeAllSame(removeSet, addSet);
        removeMenuByRole(id, removeSet);        //移除没有的菜单
        addMenuToRole(id, addSet);              //添加新的菜单
    }

    /**
     * 通过角色id获取他能访问的菜单
     * @param id 角色id
     * @return 菜单列表
     */
    public List<Menu> getMenus(Long id) {
        return menuService.getMenusByRole(roleMapper.findRoleById(id));
    }

    public void removeMenuByRole(Long rid, Collection<Long> mids) {
        if (mids == null || mids.isEmpty())
            return;
        roleMapper.removeMenuByRole(rid, mids);
    }

    public void addMenuToRole(Long rid, Collection<Long> mids) {
        if (mids == null || mids.isEmpty())
            return;
        roleMapper.addMenuToRole(rid, mids);
    }

    public void removeMenuMethodByRole(Long rid, Map<Long, List<String>> mids) {
        if (mids == null || mids.isEmpty())
            return;
        roleMapper.removeMenuMethodByRole(rid, mids);
    }

    public void addMenuMethodToRole(Long rid, Map<Long, List<String>> mids) {
        if (mids == null || mids.isEmpty())
            return;
        roleMapper.addMenuMethodToRole(rid, mids);
    }

}
