package com.ciandt.token.provider.core.usecases;

import com.ciandt.token.provider.services.EncryptServices;
import org.springframework.stereotype.Component;

@Component
public class CreateTokenUseCase {

    private EncryptServices encryptServices;

    public CreateTokenUseCase(EncryptServices encryptServices) {
        this.encryptServices = encryptServices;
    }

    public String execute(final String userName) {
        return encryptServices.encrypt(userName);
    }
}
