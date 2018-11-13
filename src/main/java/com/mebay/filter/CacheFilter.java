package com.mebay.filter;

import com.mebay.Constant;
import com.mebay.mapper.RequestMethodMapper;
import com.mebay.mapper.RoleMapper;
import com.mebay.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by gyh on 2018/11/9.
 */
@WebFilter(urlPatterns = "/system/role/*", filterName = "CacheFilter")
public class CacheFilter implements Filter {
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static final Logger logger = Logger.getLogger(CacheFilter.class.getSimpleName());
    @Autowired
    private MenuService menuService;
    @Autowired
    private RequestMethodMapper requestMethodMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletRequest hsr = (HttpServletRequest)servletRequest;
        String url = hsr.getRequestURI();
        String mode = hsr.getMethod();
        if (antPathMatcher.match("/system/role/assignment**", url) && !"GET".equals(mode)) {
            Constant.map.put("menu", menuService.getAllMenu());
            Constant.map.put("method", requestMethodMapper.getAll());
            logger.info("更新菜单缓存");
        }else if (antPathMatcher.match("/system/role/**", url) && !"GET".equals(mode)){
            Constant.map.put("Role", roleMapper.findRolesById(1L));
            logger.info("更新角色缓存");
        }
    }

    @Override
    public void destroy() {
        System.out.println("<<<<<<<<<<<<<<destroy");
    }

}
