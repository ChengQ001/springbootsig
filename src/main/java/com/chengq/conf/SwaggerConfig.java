package com.chengq.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    ConfigValueUtil configValueUtil;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).enable(configValueUtil.getSwaggerEnable())
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .select()

                .apis(RequestHandlerSelectors.basePackage("com.chengq"))
                .paths(PathSelectors.any())
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey(Constants.AUTHORIZATION, Constants.AUTHORIZATION, "header"));
        return apiKeyList;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("com.chengq")
                .description("文档")
                .termsOfServiceUrl("http://localhost/doc.html")
                .contact(new Contact("ChengQ", "http://localhost:/doc.html", "694340776@qq.com"))
                .version("1.0")
                .build();
    }


}
