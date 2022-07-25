package com.ciandt.token.provider.core.usecases;

import com.ciandt.token.provider.services.EncryptServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateTokenUseCaseTests {

    @Mock
    private EncryptServices encryptServices;

    @InjectMocks
    private CreateTokenUseCase createTokenUseCase;

    @Test
    public void shouldExecuteThenCallOneTimeEncryptService() {
        createTokenUseCase.execute("user");

        Mockito.verify(encryptServices, Mockito.only()).encrypt("user");
    }
}
