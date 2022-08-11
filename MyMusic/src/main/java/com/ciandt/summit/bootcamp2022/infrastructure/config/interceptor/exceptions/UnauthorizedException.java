package com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

}
