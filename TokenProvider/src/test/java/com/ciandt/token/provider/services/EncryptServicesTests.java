package com.ciandt.token.provider.services;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncryptServicesTests {

    @Test
    public void shouldDecryptThenReturnsOriginalValue() {
        final EncryptServices encryptServices = new EncryptServices();
        final String token = encryptServices.encrypt("user");

        final String decryptValue = encryptServices.decrypt("user", token);
        assertEquals(LocalDateTime.parse(decryptValue).truncatedTo(ChronoUnit.MINUTES), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }
}
