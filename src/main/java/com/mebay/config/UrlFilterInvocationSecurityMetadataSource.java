package com.mebay.config;

import com.mebay.bean.Menu;
import com.mebay.bean.RequestMethod;
import com.mebay.bean.Role;
import com.mebay.mapper.RequestMethodMapper;
import com.mebay.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by gyh on 2018/9/14.
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final List<Menu> allMenu;
    private final List<RequestMethod> allMethod;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final List<String> permit;

    @Autowired
    public UrlFilterInvocationSecurityMetadataSource(MenuService menuService, RequestMethodMapper requestMethodMapper) {
        //this.requestMethod = requestMethod;
        allMenu = menuService.getAllMenu();
        allMethod = requestMethodMapper.getAll();
        permit = Arrays.asList(
                "/login",

                "/druid/**",//swagger api文档一律通过
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-resources",
                "/v2/**",
                "/images/**",
                "/webjars/**",
                "/configuration/**",
                "/images/**",
                "/getAll"
        );
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        String method = ((FilterInvocation) o).getHttpRequest().getMethod();
        System.out.println(method);
        System.out.println(requestUrl);

        if (allMenu == null || allMethod == null) {
            System.out.println("<<<<<<<<<<<<出错》》》》》》》》》》》》");
        }
        /*allMenu.parallelStream().filter(menu -> antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size()>0)
                .map(menu -> menu.getRoles().stream().map(Role::getName)).toArray(String[]::new);*/

        for (Menu menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size()>0) {
                String[] values = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(values);
            }
        }
        for(String menu : permit) {
            if(antPathMatcher.match(menu, requestUrl)) {
                return null;
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
