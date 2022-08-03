package com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor;

import com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.exceptions.UnauthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Collections;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class.getName());

    @Value("${base.token.url}")
    private String baseTokenUrl;

    @Value("${token.authorizer.url}")
    private String authorizerUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String name = request.getHeader("Name");
        String token = request.getHeader("Token");

        if (StringUtils.isBlank(name) || StringUtils.isBlank(token)) {
            logger.warn("Credentials invalid, throwing exception!");
            throw new UnauthorizedException("Unauthorized request. To make any request to the MyMusic API, enter a username in the 'Name' header and a token in the 'Token' header.");
        }

        URI url = new URI(String.format("%s%s", baseTokenUrl, authorizerUrl));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        TokenRequest tokenRequest = new TokenRequest(name, token);
        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(tokenRequest);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

            return responseEntity.getStatusCodeValue() == 201;
        } catch (Exception ex) {
            throw new UnauthorizedException("Unauthorized request.");
        }
    }

}
