package com.ccyw.springboot.own.common.configure;

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

/**
 * @author luoc
 * @version V1.0.0
 * @description Swagger2配置类
 * @date 2018/8/14 22:51
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ccyw.springboot.own"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Jdbc中使用Swagger2构建RESTful APIS")
                .description("简单优雅的restful风格")
                .termsOfServiceUrl("http://www.ccyw.com/")
                .contact(new Contact("admin", "http://www.ccyw.com/", "luocong2017@gmail.com"))
                .version("1.0.0")
                .build();
    }
}
