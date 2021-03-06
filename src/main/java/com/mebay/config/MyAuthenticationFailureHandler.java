package com.mebay.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mebay.bean.RespBody;
import com.mebay.common.Util;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败响应
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        RespBody<String> respBean = new RespBody<>(0, "登录失败!");
        if (e instanceof UsernameNotFoundException) {
            respBean.put(0, "没有该用户，登录失败!");
        }else if (e instanceof BadCredentialsException) {
            respBean.put(0, "密码错误，登录失败!");
        }else if (e instanceof InsufficientAuthenticationException) {
            respBean.put(0, "单位不匹配，登录失败!");
        }else if (e instanceof DisabledException) {
            respBean.put(0, "账户被禁用，登录失败，请联系管理员!");
        }
        Util.addHeader(httpServletResponse, httpServletRequest);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(respBean));
    }
}