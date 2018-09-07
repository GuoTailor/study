package com.study.config;

import com.study.filter.JWTAuthenticationFilter;
import com.study.filter.JWTLoginFilter;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final MyAuthenticationFailureHandler failureHandler;
    private final MyAuthenticationSuccessHandler successHandler;
    private final AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Autowired
    public WebSecurityConfig(UserService userService, MyAuthenticationFailureHandler failureHandler, MyAuthenticationSuccessHandler successHandler, AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler) {
        this.userService = userService;
        System.out.println("nmka");
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {

        /*http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic()
                .authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/select").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successHandler) // 登录成功
                .failureHandler(failureHandler) // 登录失败
                .permitAll();

        http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(userService).tokenValiditySeconds(5 * 60);*/

        http
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()

                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/",
                        "/register",
                        "/login",
                        "/system",
                        "/druid/**",
                        "/swagger-ui.html",
                        "/test/**",
                        "/getAll").permitAll()

                // 除上面外的所有请求全部需要鉴权认证
                //.anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler)
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager(), failureHandler, successHandler))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userService));

        // 禁用缓存
        http.headers().cacheControl();
        authenticationManager();
    }
}