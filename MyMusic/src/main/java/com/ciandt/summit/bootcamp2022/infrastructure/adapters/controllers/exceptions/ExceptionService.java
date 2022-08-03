package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions;

import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ExceptionService {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    ResponseEntity<BusinessRuleException> handleBusinessRuleException(BusinessRuleException err) {
        DefaultResponseError defaultResponseError = new DefaultResponseError();
        defaultResponseError.setStatus(HttpStatus.BAD_REQUEST.value());
        defaultResponseError.setMessage(err.getMessage());
        return new ResponseEntity(defaultResponseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<UnauthorizedException> handleUnauthorizedException(UnauthorizedException err) {
        DefaultResponseError defaultResponseError = new DefaultResponseError();
        defaultResponseError.setStatus(HttpStatus.FORBIDDEN.value());
        defaultResponseError.setMessage(err.getMessage());
        return new ResponseEntity(defaultResponseError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException err) {

        List<String> errors = new ArrayList<>();
        List<FieldError> fieldErrors = err.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            String message = messageSource.getMessage(fieldError, Locale.US);
            String error = String.format("Field: %s - Error: %s", fieldError.getField(), message);
            errors.add(error);
        });

        return new ResponseEntity<>(new FieldValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), "Some fields were not filled in correctly", errors), HttpStatus.BAD_REQUEST);
    }

}
