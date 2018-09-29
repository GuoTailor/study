package com.mebay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.mebay.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("api文档")
                .description("restfun 风格接口\n" +
                        "这里面的所有接口访问都需要权限验证\n" +
                        "都要在请求头中加入”authorization: Bearer “\n" +
                        "在空格后面加token\n" +
                        "用户登陆成功后返回的头中会有”authorization: Bearer token“")
                //服务条款网址
                //.termsOfServiceUrl("https://baike.baidu.com/search/none?word=%E9%83%AD%E9%9B%A8%E8%A1%A1&pn=0&rn=10&enc=utf8")
                .version("0.0.1")
                .contact(new Contact("作者首页", "https://baike.baidu.com/search/none?word=%E9%83%AD%E9%9B%A8%E8%A1%A1&pn=0&rn=10&enc=utf8", "2350871838@qq.com"))
                .build();
    }
}
