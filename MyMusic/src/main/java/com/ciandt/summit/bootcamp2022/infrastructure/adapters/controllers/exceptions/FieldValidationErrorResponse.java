package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldValidationErrorResponse {

    private String field;
    private String message;

}
