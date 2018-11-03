package com.mebay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${version}")
    private String version;

    @Bean
    public Docket api() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.mebay.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
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
                .version(version)
                .contact(new Contact("作者", "https://baike.baidu.com/search/none?word=%E9%83%AD%E9%9B%A8%E8%A1%A1&pn=0&rn=10&enc=utf8", "2350871838@qq.com"))
                .extensions(Collections.singletonList(new VendorExtension<String>() {
                    @Override
                    public String getName() {
                        return  "看看";
                    }

                    @Override
                    public String getValue() {
                        return "ksk";
                    }
                }))
                .build();
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider) {
        return () -> {
            SwaggerResource wsResource = new SwaggerResource();
            wsResource.setName("铭贝api开发规范-yaml");
            wsResource.setSwaggerVersion("2.0");
            wsResource.setUrl("/swagger.yaml");

            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
            resources.add(wsResource);
            return resources;
        };
    }

}
