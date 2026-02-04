package com.xjy.shujia.config;

import io.swagger.annotations.SwaggerDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /**
     * 配置了Swagger 的Docket的bean实例,扫描接口的位置
     * .apis
     *   RequestHandlerSelectors 配置swagger扫描接口的方式
     *      basePackage() 指定要扫描哪些包
     *      any() 全部都扫描
     *      none() 全部不扫描
     *      withClassAnnotation() 扫描类上的注解 参数是一个注解的反射对象
     *      withMethodAnnotation() 扫描包上的注解
     * .paths
     *   PathSelectors 路径扫描接口
     *      ant 配置以xxx 开头的路径
     * @return
     */
    @Bean
    public Docket docket( ){

        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("xjy")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xjy.shujia.controller"))
                //.paths(PathSelectors.ant("/hello/**"))
                .build();//构建者模式
    }
    /**
     * 配置Swagger信息 apiinfo
     * @return
     */
    private ApiInfo apiInfo(){
        //配置作者信息
        Contact DEFAULT_CONTACT = new Contact("xjy", "https://xjy.plus/", "2642630566@qq.com");
        return  new ApiInfo(
                "Swagger API文档",
                "暑假训练",
                "v1.0",
                "https://xjy.plus/",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
