package com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor;

import com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.exceptions.NaoAutorizadoException;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String name = request.getHeader("Name");
        String token = request.getHeader("Token");

        if (name == null || token == null || name.isBlank() || token.isBlank() || name.isEmpty() || token.isEmpty()) {
            throw new NaoAutorizadoException("Requisição não autorizada. Para Realizar Qualquer Requisição na API MyMusic Informe um Nome de Usuario no Header 'Name' e um Token no Header 'Token'.");
        }

        final String baseUrl = "https://baygon-token-provider.herokuapp.com/api/v1/token/authorizer";
        URI url = new URI(baseUrl);

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
            throw new NaoAutorizadoException("Requisição não autorizada.");
        }
    }

}
