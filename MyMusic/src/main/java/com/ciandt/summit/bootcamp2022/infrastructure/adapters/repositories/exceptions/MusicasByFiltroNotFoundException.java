package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.exceptions;

public class MusicasByFiltroNotFoundException extends RuntimeException {

    public MusicasByFiltroNotFoundException(String message) {
        super(message);
    }

}