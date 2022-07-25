package com.ciandt.token.provider.core.usecases;

import com.ciandt.token.provider.services.EncryptServices;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateAuthorizerUseCaseTests {

    @Mock
    private EncryptServices encryptServices;

    @InjectMocks
    private CreateAuthorizerUseCase createAuthorizerUseCase;

    @Test
    public void shouldExecuteWhenExpiredTokenThenThrowsSecurityException() {
        Mockito.when(encryptServices.decrypt("user", "12345")).thenReturn(LocalDateTime.now().minusMinutes(50000L).toString());
        final Throwable thrown = catchThrowable(() -> createAuthorizerUseCase.execute("user", "12345"));

        assertThat(thrown).isInstanceOf(SecurityException.class);
    }

    @Test
    public void shouldExecuteWhenNotExpiredTokenThenReturnsOk() {
        Mockito.when(encryptServices.decrypt("user", "12345")).thenReturn(LocalDateTime.now().toString());
        final String token = createAuthorizerUseCase.execute("user", "12345");

        assertEquals("ok", token);
    }

}
