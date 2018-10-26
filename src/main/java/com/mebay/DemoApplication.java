package com.mebay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

@SpringBootApplication
// 添加servlet组件扫描，使得Spring能够扫描到我们编写的servlet和filter
@ServletComponentScan
//@EnableTransactionManagement
@MapperScan("com.mebay.mapper")
public class DemoApplication {
    private static Logger logger = Logger.getLogger(DemoApplication.class.getSimpleName());

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        logger.info("nmka");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("c");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        System.out.println(">>>>>>>>>>>");
        return new CorsFilter(source);
    }


}
