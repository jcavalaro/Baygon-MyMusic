package com.ciandt.token.provider.services;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EncryptServices {

    public String encrypt(final String userName) {
        StandardPBEStringEncryptor encryptor = createEncryptor(userName);

        final String dateTime = LocalDateTime.now().toString();

        return encryptor.encrypt(dateTime);
    }

    public String decrypt(final String userName, final String token) {
        StandardPBEStringEncryptor encryptor = createEncryptor(userName);

        return encryptor.decrypt(token);
    }

    private StandardPBEStringEncryptor createEncryptor(String userName) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setIvGenerator(new RandomIvGenerator());
        encryptor.setPassword(userName);
        return encryptor;
    }
}
