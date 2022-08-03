package com.ciandt.summit.bootcamp2022.domain.services.exceptions;

public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }

}