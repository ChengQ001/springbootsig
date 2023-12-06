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

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    ConfigValueUtil configValueUtil;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(configValueUtil.getSwaggerEnable())
                .apiInfo(apiInfo())
                // .host("http://localhost:3350")
                .select()


                .apis(RequestHandlerSelectors.basePackage("com.chengq.businessmodule"))
                .paths(PathSelectors.any())
                .build()
                // 支持的通讯协议集合
                .protocols(newHashSet("http", "https"))
                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())
                ;

    }

    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey(Constants.AUTHORIZATION, Constants.AUTHORIZATION, "header"));
        return apiKeyList;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ChengQ文档")
                .description("ChengQ文档")
                .termsOfServiceUrl("http://localhost/doc.html")
                .contact(new Contact("ChengQ", "http://localhost:3350/doc.html", "694340776@qq.com"))
                .version("1.0")
                .build();
    }


}
