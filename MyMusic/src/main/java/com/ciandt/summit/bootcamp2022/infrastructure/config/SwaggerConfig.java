package com.ciandt.summit.bootcamp2022.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info().title("MyMusic API").description("MyMusic Squad Baygon").version("v1.0").contact(new Contact().name("Baygon").url("https://github.com/jcavalaro/Baygon-MyMusic")));
    }

}
