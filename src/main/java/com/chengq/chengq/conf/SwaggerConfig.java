package com.chengq.chengq.conf;

import com.chengq.chengq.tools.ConfigValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ConfigValueUtil configValueUtil;

    private Boolean getEnable() {
        try {
            return configValueUtil.getEnvironment().equals("dev") && configValueUtil.getSwaggerEnable();
        } catch (Exception ex) {
            return false;
        }
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).enable(getEnable())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chengq.chengq"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("com.chengq")
                .description("文档")
                .termsOfServiceUrl("https://localhost/doc.html")
                .contact(new Contact("ChengQ", "https://localhost/doc.html", "694340776@qq.com"))
                .version("1.0")
                .build();
    }


}
