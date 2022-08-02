package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions;

import com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.exceptions.NaoAutorizadoException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.RuleLengthViolationException;
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
        errorResponse.setMessage(err.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<NaoAutorizadoException> handleNaoAutorizadoException(NaoAutorizadoException err) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(err.getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
