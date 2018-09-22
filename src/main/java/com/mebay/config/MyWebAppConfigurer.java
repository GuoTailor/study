package com.mebay.config;

import com.mebay.filter.LogAspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
        System.out.println("<<<<<<<<<<<<<nmak2>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogAspect()).addPathPatterns("/**");
        System.out.println("<<<<<<<<<<<<<nmak1>>>>>>>>>>>>>>>>>>>");
    }

}
