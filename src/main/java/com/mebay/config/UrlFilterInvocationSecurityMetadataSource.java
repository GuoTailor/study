package com.mebay.config;

import com.mebay.bean.Menu;
import com.mebay.bean.Role;
import com.mebay.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * Created by gyh on 2018/9/14.
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final List<Menu> allMenu;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    public UrlFilterInvocationSecurityMetadataSource(MenuService menuService) {
        allMenu = menuService.getAllMenu();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        ((FilterInvocation) o).getHttpRequest().getMethod();
        System.out.println(requestUrl);
        if ("/login".equals(requestUrl)) {
            return null;
        }
        if (allMenu == null)
            System.out.println("<<<<<<<<<<<<出错》》》》》》》》》》》》");
        /*allMenu.parallelStream().filter(menu -> antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size()>0)
                .map(menu -> menu.getRoles().stream().map(Role::getName)).toArray(String[]::new);*/

        for (Menu menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size()>0) {
                String[] values = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(values);
            }
        }
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        System.out.println(">>>>>>>>>>>>>要什么全部权限");
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
