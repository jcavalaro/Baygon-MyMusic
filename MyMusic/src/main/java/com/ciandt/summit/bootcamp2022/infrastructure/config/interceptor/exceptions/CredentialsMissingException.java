package com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.exceptions;

public class CredentialsMissingException extends RuntimeException {

    public CredentialsMissingException(String message) {
        super(message);
    }

}
