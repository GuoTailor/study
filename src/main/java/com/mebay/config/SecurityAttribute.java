package com.mebay.config;

import com.mebay.bean.Menu;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义的权限属性
 */
public class SecurityAttribute implements ConfigAttribute {
    private final String attrib;
    private final String url;
    private final List<String> method;

    public SecurityAttribute(String attrib, String url, List<String> method) {
        this.attrib = attrib;
        this.url = url;
        this.method = method;
    }

    @Override
    public String getAttribute() {
        return attrib;
    }

    public List<String> getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public static List<ConfigAttribute> createList(Menu menu, String method) {
        Assert.notNull(menu, "您必须提供一系列权限url访问方法等信息");
        List<ConfigAttribute> confs = menu.getRoles().stream().filter(r -> r.getMethod().contains(method)).map(r -> new SecurityAttribute(r.getName(), menu.getUrl(), r.getMethod())).collect(Collectors.toList());
        return confs.isEmpty() ? Collections.singletonList(new SecurityAttribute("ROLE_LOGIN", menu.getUrl(), null)) : confs;
    }
}
