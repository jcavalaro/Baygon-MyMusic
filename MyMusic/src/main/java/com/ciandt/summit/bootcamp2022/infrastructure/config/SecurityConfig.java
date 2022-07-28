package com.ciandt.summit.bootcamp2022.infrastructure.config;

import com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/webjars/**")
                .addPathPatterns("/**");
    }

}
