package com.ciandt.token.provider.core.usecases;

import com.ciandt.token.provider.services.EncryptServices;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateAuthorizerUseCase {

    private EncryptServices encryptServices;

    public CreateAuthorizerUseCase(EncryptServices encryptServices) {
        this.encryptServices = encryptServices;
    }

    public String execute(final String userName, final String token) {
        final String session = encryptServices.decrypt(userName, token);
        if (isExpiredToken(session)) {
            throw new SecurityException("Token expirado");
        }
        return "ok";
    }

    private boolean isExpiredToken(final String token) {
        final LocalDateTime dateTime = LocalDateTime.parse(token);
        final LocalDateTime dateLimit = dateTime.plusMinutes(1);
        return dateLimit.compareTo(LocalDateTime.now()) < 0;
    }
}
