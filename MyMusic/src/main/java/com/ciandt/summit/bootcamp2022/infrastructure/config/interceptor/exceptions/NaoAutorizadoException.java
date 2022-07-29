package com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.exceptions;

public class NaoAutorizadoException extends RuntimeException {

    public NaoAutorizadoException(String message) {
        super(message);
    }

}
