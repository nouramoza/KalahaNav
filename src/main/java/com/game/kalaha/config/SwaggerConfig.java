package com.game.kalaha.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/api/.*"))
            .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("KALAHA API",
            "GAME",
            "1.0",
            "Term of service",
            new Contact(" KALAHA Game Support Team ", "http://kalaha.com", "support@kalaha.com"),
            "",
            "http://kalaha.com", new ArrayList());


        return apiInfo;

    }

    private ApiKey apiKey() {
        return new ApiKey("apikey", "Authorization", "header");
    }

    private Predicate<String> paths() {
        return or(
            regex("/api/*.*"));
    }

}
