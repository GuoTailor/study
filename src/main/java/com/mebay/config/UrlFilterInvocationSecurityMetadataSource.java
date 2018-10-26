package com.mebay.config;

import com.mebay.bean.Menu;
import com.mebay.bean.RequestMethod;
import com.mebay.common.Util;
import com.mebay.mapper.RequestMethodMapper;
import com.mebay.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Created by gyh on 2018/9/14.
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static final Logger logger = Logger.getLogger(UrlFilterInvocationSecurityMetadataSource.class.getSimpleName());
    private List<Menu> allMenu;
    private final MenuService menuService;
    private final RequestMethodMapper requestMethodMapper;
    private List<RequestMethod> allMethod;
    private final List<String> permit;
    public static boolean INVALID = true;

    @Autowired
    public UrlFilterInvocationSecurityMetadataSource(MenuService menuService, RequestMethodMapper requestMethodMapper) {
        //this.requestMethod = requestMethod;
        this.menuService = menuService;
        this.requestMethodMapper = requestMethodMapper;
        permit = Arrays.asList(
                "/login",
                "/update",
                "/menu",
                "/error",
                "/index.html",

                "/druid([?/].*|$)",//swagger api文档一律通过
                "/swagger-resources([?/].*|$)",
                "/swagger-ui.html",
                "/swagger-resources",
                "/v2([?/].*|$)",
                "/images([?/].*|$)",
                "/webjars([?/].*|$)",
                "/",
                "/configuration([?/].*|$)",
                "/images([?/].*|$)",
                "/getUsersByDeptId",
                "/favicon.ico",

                "/[\\w/]*\\.json",
                "/[\\w/]*\\.yaml",
                "/resources([?/].*|$)"
        );
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        String method = ((FilterInvocation) o).getHttpRequest().getMethod();
        Util.addHeader(((FilterInvocation) o).getHttpResponse(), ((FilterInvocation) o).getHttpRequest());
        if (INVALID) {
            update();
        }
        if (allMenu == null || allMethod == null) {
            System.out.println("<<<<<<<<<<<<出错");
            System.exit(0);
        }
        if (method.equals("OPTIONS")){
            logger.info("OPTIONS");
            return null;
        }
        //TODO 菜单刷新问题
        for (Menu menu : allMenu) {
            if (Pattern.compile(menu.getUrl()).matcher(requestUrl).matches() && menu.getRoles().size() > 0) {
                logger.info(method + "访问:" + menu.getUrl());
                return SecurityAttribute.createList(menu, method);
            }
        }
        for(String menu : permit) {
            if(Pattern.compile(menu).matcher(requestUrl).matches()) {
                return null;
            }
        }
        //没有匹配上的资源，都是无权访问
        logger.info(method + "无权限配置" + requestUrl);
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

    public void update() {
        allMenu = menuService.getAllMenu();
        allMethod = requestMethodMapper.getAll();
        INVALID = false;
        logger.info("更新菜单");
    }

}
