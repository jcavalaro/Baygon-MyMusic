package com.ciandt.token.provider.controllers;

import com.ciandt.token.provider.controllers.dto.request.CreateAuthorizerRequest;
import com.ciandt.token.provider.controllers.dto.request.CreateAuthorizerRequestData;
import com.ciandt.token.provider.core.usecases.CreateAuthorizerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/token/authorizer")
@Tag(description = "Api para Gerar e Validar Token", name = "Token Controller")
public class TokenAuthorizerController {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthorizerController.class);
    private final CreateAuthorizerUseCase createAuthorizerUseCase;

    public TokenAuthorizerController(CreateAuthorizerUseCase createAuthorizerUseCase) {
        this.createAuthorizerUseCase = createAuthorizerUseCase;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Validar Token", description = "Valida um Token a partir de uma Chave (Nome) e um Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Token validado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro na requisição")
    })
    public ResponseEntity<String> createTokenAuthorizer(@RequestBody CreateAuthorizerRequest createAuthorizerRequest) {
        try {
            CreateAuthorizerRequestData data = createAuthorizerRequest.getData();
            logger.info("Recebido requisição para geração de token: "+ data.getName());
            String token = createAuthorizerUseCase.execute(data.getName(), data.getToken());
            logger.info("Token validado com sucesso");
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        } catch (SecurityException e) {
            logger.error("Requisição inválida: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }
}
