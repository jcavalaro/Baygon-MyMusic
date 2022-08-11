package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldValidationErrorResponse {

    private int status;
    private String message;
    private List<String> errors;

}
