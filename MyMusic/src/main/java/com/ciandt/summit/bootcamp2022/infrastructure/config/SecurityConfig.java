package com.ciandt.summit.bootcamp2022.infrastructure.config;

import com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Component
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/configuration/ui")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/configuration/security")
                .excludePathPatterns("/webjars/**")
                .addPathPatterns("/**");
    }

}
