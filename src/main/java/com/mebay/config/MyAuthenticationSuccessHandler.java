package com.mebay.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mebay.Constant;
import com.mebay.bean.RespBody;
import com.mebay.bean.User;
import com.mebay.common.Util;
import com.mebay.filter.token.TokenMgr;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功的处理
 * 返回token
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        String token = TokenMgr.createJWT(user.getId().toString(), user.getUsername(), user.getRoleString(), user.getDepId().toString(), Constant.JWT_TTL);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.addHeader("Authorization", "Bearer " + token);
        Util.addHeader(httpServletResponse, httpServletRequest);
        RespBody<String> respBean = new RespBody<>(1, user.getUsername());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(respBean));
    }
}
