package com.mebay.config;

import com.mebay.filter.JWTAuthenticationFilter;
import com.mebay.filter.JWTLoginFilter;
import com.mebay.service.UserService;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final MyAuthenticationFailureHandler failureHandler;
    private final MyAuthenticationSuccessHandler successHandler;
    private final AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    private final UrlAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    public WebSecurityConfig(UserService userService, MyAuthenticationFailureHandler failureHandler, MyAuthenticationSuccessHandler successHandler, AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler, UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource, UrlAccessDecisionManager urlAccessDecisionManager) {
        this.userService = userService;
        System.out.println("nmka");
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;
        this.urlAccessDecisionManager = urlAccessDecisionManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                //.and()
                //.headers().frameOptions().disable()
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