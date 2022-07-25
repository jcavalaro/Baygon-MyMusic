package com.ciandt.token.provider.controllers;

import com.ciandt.token.provider.controllers.dto.request.CreateTokenRequest;
import com.ciandt.token.provider.controllers.dto.request.CreateTokenRequestData;
import com.ciandt.token.provider.core.usecases.CreateTokenUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthorizerController.class);
    private final CreateTokenUseCase createTokenUseCase;

    public TokenController(CreateTokenUseCase createTokenUseCase) {
        this.createTokenUseCase = createTokenUseCase;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createToken(@RequestBody CreateTokenRequest createTokenRequest) {
        try {
            CreateTokenRequestData data = createTokenRequest.getData();
            logger.info("Recebido requisição para criação de token para chave: "+ data.getName());
            String token = createTokenUseCase.execute(data.getName());
            logger.info("Token gerado com sucesso");
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erro na requisição: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
