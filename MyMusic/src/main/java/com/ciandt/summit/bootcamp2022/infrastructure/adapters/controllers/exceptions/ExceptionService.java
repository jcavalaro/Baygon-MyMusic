package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionService {

    @ExceptionHandler
    ResponseEntity<RuleLengthViolationException> handleRuleLengthViolationException(RuleLengthViolationException err) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Filtro deve ter ao menos 2 caracteres.");
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
