package com.ciandt.token.provider.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info().title("Token Provider API").description("Token Provider Squad Baygon").version("v1.0").contact(new Contact().name("Baygon").url("https://github.com/jcavalaro/Baygon-MyMusic")));
    }

}
