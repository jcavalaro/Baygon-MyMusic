package com.ciandt.summit.bootcamp2022.domain.services.exceptions;

public class RuleLengthViolationException extends RuntimeException {

    public RuleLengthViolationException(String message) {
        super(message);
    }

}