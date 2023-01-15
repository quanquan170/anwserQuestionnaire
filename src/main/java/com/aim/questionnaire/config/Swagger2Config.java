package com.aim.questionnaire.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config {

    @Value("${swagger.enable}")
    private boolean enable;

    @Bean
    public Docket api() {
        //添加head参数start
        RequestParameterBuilder tokenPar = new RequestParameterBuilder();
        List<RequestParameter> pars = new ArrayList<>();

        tokenPar.name("Authentication").description("令牌").in(ParameterType.HEADER).query(param -> param.model(model -> model.scalarModel(ScalarType.STRING))).required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.aim.questionnaire.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot anwserQuestionnaire问卷调查 API")
                .description("问卷调查系统相关接口")
                .version("1.0")
                .contact(new Contact("425", "http://localhost:8085/doc.html", "1480105462@qq.com"))
                .build();
    }
}
