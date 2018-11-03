package com.mebay.config;

import com.mebay.filter.JWTAuthenticationFilter;
import com.mebay.filter.JWTLoginFilter;
import com.mebay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsUtils;

/**
 * SpringSecurity 的配置类
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final MyAuthenticationFailureHandler failureHandler;
    private final MyAuthenticationSuccessHandler successHandler;
    private final AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    private final UrlAccessDecisionManager urlAccessDecisionManager;
    private final MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    public WebSecurityConfig(UserService userService, MyAuthenticationFailureHandler failureHandler, MyAuthenticationSuccessHandler successHandler, AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler, UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource, UrlAccessDecisionManager urlAccessDecisionManager, MyAuthenticationProvider myAuthenticationProvider) {
        this.userService = userService;
        System.out.println("nmka");
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;
        this.urlAccessDecisionManager = urlAccessDecisionManager;
        this.myAuthenticationProvider = myAuthenticationProvider;
    }

    /**
     * 配置自定义的用户权限验证
     * @param auth 认证管理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
        myAuthenticationProvider.setUserService(userService);   //配置用户的加载类
        myAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());   //配置密码加密算法
        auth.authenticationProvider(myAuthenticationProvider);  //配置自己的用户验证类
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
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource); //配置自己的url权限
                        o.setAccessDecisionManager(urlAccessDecisionManager);   //配置自己的url鉴权
                        return o;
                    }
                })
                //.and()
                //.headers().frameOptions().disable()
                .and()
                .exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler)
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager(), failureHandler, successHandler))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));

        // 禁用缓存
        http.headers().cacheControl();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
}