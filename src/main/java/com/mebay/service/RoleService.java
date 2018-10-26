package com.mebay.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mebay.Constant;
import com.mebay.bean.*;
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
    public RoleService(RoleMapper roleMapper, MenuService menuService) {
        this.roleMapper = roleMapper;
        this.menuService = menuService;
    }

    public PageView<Role> roles(PageQuery pageQuery) {
        Page<Role> page = PageHelper.startPage(pageQuery);
        roleMapper.findAllRole(pageQuery.buildSubSql());
        return PageView.build(page);
    }

    public Role getRolesById(Long id) {
        return roleMapper.findRolesById(id);
    }

    public int addNewRole(String role, String roleZh) {
        if (role == null || "".equals(role))
            return -1;
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        if (roleMapper.findRolesByName(role, roleZh) != null)
            return -2;
        return roleMapper.addNewRole(role, roleZh);
    }

    public Role findRolesByName(String role, String roleZh) {
        return roleMapper.findRolesByName(role, roleZh);
    }

    public int deleteRoleById(Long rid) {
        return roleMapper.deleteRoleById(rid);
    }

    public int updateRole(Role role, Long id) {
        if (Util.isEmpty(role))
            return -1;
        return roleMapper.updateRole(role, id);
    }

    public void update(Map<Long, List<String>> menuRoles, Long id) {
        UrlFilterInvocationSecurityMetadataSource.INVALID = true;
        List<Menu> menus = menuService.getMenusByRole(roleMapper.findRoleById(id));
        Map<Long, List<String>> remove = menus.stream().collect(Collectors.toMap(Menu::getId, ms -> ms.getRoles().get(0).getMethod()));
        for (Map.Entry<Long, List<String>> entry : menuRoles.entrySet()) {
            List<String> roleList;
            if ((roleList = remove.get(entry.getKey())) != null) {  //在已有的权限中找请求的菜单id
                List<String> strings = entry.getValue();
                removeAllSame(roleList, strings);//去掉相同的
            }
        }
        removeMenuMethodByRole(id, remove);     //移除没有的菜单访问方法
        addMenuMethodToRole(id, menuRoles);     //添加新的菜单访问方法
        Set<Long> removeSet = remove.keySet();
        Set<Long> addSet = menuRoles.keySet();
        removeAllSame(removeSet, addSet);
        removeMenuByRole(id, removeSet);        //移除没有的菜单
        addMenuToRole(id, addSet);              //添加新的菜单
    }

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

    public <T> void removeAllSame(Collection<T> c1,Collection<T> c2) {
        c1.removeIf(c -> {
            if (c2.contains(c)) {
                c2.remove(c);
                return true;
            }
            return false;
        });
    }

}
