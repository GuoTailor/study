package com.mebay.config;

import com.mebay.filter.LogAspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Value("${resourcesPath}")
    private String resourcesPath;

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            String path = new File(resourcesPath).getCanonicalPath();
            System.out.println(path + " " + System.getProperty("os.name"));
            registry.addResourceHandler("/resources/**").addResourceLocations("file:" + path + "/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
