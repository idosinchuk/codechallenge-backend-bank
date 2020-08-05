package com.idosinchuk.codechallenge.backend.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.idosinchuk.codechallenge.backend.bank.infrastructure.web"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    // Describe swagger api
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger codechallenge-backend-bank")
                .description("This page lists all the rest apis for Swagger codechallenge-backend-bank")
                .version("1.0-SNAPSHOT").build();
    }

    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public String redirect() {
        return "redirect:swagger-ui.html";
    }
}
