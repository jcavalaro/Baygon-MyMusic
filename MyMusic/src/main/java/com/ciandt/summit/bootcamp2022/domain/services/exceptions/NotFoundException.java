package com.ciandt.summit.bootcamp2022.domain.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NotFoundException extends RuntimeException { }