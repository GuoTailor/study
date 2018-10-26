package com.mebay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * Created by gyh on 2018/9/14.
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    private static final Logger logger = Logger.getLogger(UrlAccessDecisionManager.class.getSimpleName());

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException {
        String method = ((FilterInvocation) o).getHttpRequest().getMethod();
        logger.info("方法:" + method);
        for (ConfigAttribute ca : collection) {
            //当前请求需要的权限
            String needRole = ca.getAttribute();
            SecurityAttribute sa = null;

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            //当前用户所具有的权限
            if (ca instanceof SecurityAttribute) {
                sa = ((SecurityAttribute)ca);
            }
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_SUPER_ADMIN"))
                    return;
                if (authority.getAuthority().equals(needRole)) {
                    if (sa != null && sa.getMethod().contains(method))
                        return;
                }
            }
        }
        logger.info("权限不足!已拒绝访问" + authentication.getAuthorities());
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}