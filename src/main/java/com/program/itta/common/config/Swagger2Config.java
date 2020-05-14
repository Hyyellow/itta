package com.program.itta.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: itta
 * @description: Swagger配置文件
 * @author: Mr.Huang
 * @create: 2020-05-14 21:25
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Value("${swagger.host}")
    private String host;

    /**
     * 业务系统的token认证机制
     */
    private List<Parameter> getTokenPar(){
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization")
                .description("鉴权token")
                .modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .host(host)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.program.itta.controller")) // 过滤的接口
                .paths(PathSelectors.any())
                .build();
        //.globalOperationParameters(getTokenPar());
    }

    private ApiInfo getApiInfo() {
        Contact contact = new Contact("ITTA团队","url","570911275@qq.com");
        return new ApiInfoBuilder()
                .title("【ITTA】微信小程序")
                .description("项目描述：项目任务管理小程序")
                .version("版本2.0")
                .termsOfServiceUrl("http://"+ host +"/")
                .contact(contact)
                .build();
    }
}
