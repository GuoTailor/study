package com.study.config;

import com.google.gson.Gson;
import com.study.bean.RespBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        RespBean respBean = new RespBean("400", "登录失败!");
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            respBean.setMessage("用户名或密码输入错误，登录失败!");
        } else if (e instanceof DisabledException) {
            respBean.setMessage("账户被禁用，登录失败，请联系管理员!");
        }
        httpServletResponse.getWriter().write(new Gson().toJson(respBean));
    }
}