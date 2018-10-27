package com.mebay.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mebay.bean.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationSuccessHandler successHandler;

    public JWTLoginFilter(AuthenticationManager authenticationManager, AuthenticationFailureHandler failureHandler, AuthenticationSuccessHandler successHandler) {
        this.authenticationManager = authenticationManager;
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        User user = new User();
        String username = obtainUsername(req);
        String password = obtainPassword(req);
        String superiorName = req.getParameter("superiorName");
        logger.info("nmka>>>>>" + username + " " + password + "  " + superiorName);
        if (username == null || password == null || superiorName == null) {
            try {
                user = new ObjectMapper().readValue(req.getInputStream(), User.class);
                logger.info(user.toString());
            } catch (IOException e) {
                logger.info("登陆失败，账号密码解析错误");
                user.setUsername(username);
                user.setPassword(password);
            }
        } else {
            user.setUsername(username);
            user.setPassword(password);
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(),
                user.getPassword());
        authRequest.setDetails(superiorName);
        System.out.println(getAuthenticationManager() == null);
        return authenticationManager.authenticate(authRequest);
    }

    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        successHandler.onAuthenticationSuccess(req, res, auth);
        //String token = TokenMgr.createJWT(((User) auth.getPrincipal()).getId() + "", ((User) auth.getPrincipal()).getUsername(), Constant.JWT_TTL);
        //res.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

}

