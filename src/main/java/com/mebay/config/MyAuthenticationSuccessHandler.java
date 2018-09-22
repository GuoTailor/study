package com.mebay.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mebay.Constant;
import com.mebay.bean.RespBean;
import com.mebay.bean.User;
import com.mebay.filter.token.TokenMgr;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String token = TokenMgr.createJWT(user.getId() + "", user.getUsername(), user.getRoleString(), user.getDepId().toString(), Constant.JWT_TTL);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.addHeader("Authorization", "Bearer " + token);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE ,PUT");
        httpServletResponse.setHeader("Access-Control-Max-Age", "30");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since,"
                + " Pragma, Last-Modified, Cache-Control, Expires, Content-Type, "
                + "X-E4M-With,userId,token,Authorization,deviceId,Access-Control-Allow-Origin,Access-Control-Allow-Headers,Access-Control-Allow-Methods");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("XDomainRequestAllowed", "1");

        RespBean respBean = new RespBean("200", user.getUsername());
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(respBean));
    }
}
