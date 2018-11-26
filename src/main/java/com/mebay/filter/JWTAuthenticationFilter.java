package com.mebay.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mebay.bean.Role;
import com.mebay.bean.User;
import com.mebay.filter.token.CheckPOJO;
import com.mebay.filter.token.TokenMgr;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 每一次url访问都会调用此类
 * 登陆的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String authToken = authHeader.replaceFirst("Bearer ", ""); // The part after "Bearer "
            CheckPOJO checkPOJO = TokenMgr.validateJWT(authToken);

            if (checkPOJO.isSuccess()) {
                Claims claims = checkPOJO.getClaims();
                logger.info("checking authentication " + claims.getSubject());
                if (claims.getSubject() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = new User();
                    user.setId(Long.parseLong(claims.getId()));
                    user.setName(claims.getSubject());
                    user.setDepId(Long.parseLong(claims.getIssuer()));
                    user.setAuthorities(new ObjectMapper().readValue(checkPOJO.getClaims().getAudience(), new TypeReference<List<Role>>() {}));
                    //System.out.println(user.getRoleString());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            claims.getSubject(), null, User.creationAut(new ObjectMapper().readValue(checkPOJO.getClaims().getAudience(), new TypeReference<List<String>>() {})));
                    authentication.setDetails(user);
                    logger.info("authenticated user " + claims.getSubject() + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

}
