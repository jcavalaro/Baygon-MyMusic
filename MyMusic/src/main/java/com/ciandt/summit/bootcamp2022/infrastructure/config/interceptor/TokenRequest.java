package com.ciandt.summit.bootcamp2022.infrastructure.config.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TokenRequest {
    private Data data;

    public TokenRequest(String name, String token) {
        this.data = new Data(name, token);
    }

    @AllArgsConstructor
    @Getter
    static class Data {
        private String name;
        private String token;
    }

}

