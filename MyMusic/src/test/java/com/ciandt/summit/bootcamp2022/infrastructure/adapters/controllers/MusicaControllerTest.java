package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicaServicePort;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;

@WebMvcTest
class MusicaControllerTest {

    @Autowired
    private MusicaController musicaController;

    @MockBean
    private MusicaServicePort musicaServicePortice;



    @BeforeEach
    void setup(){
        RestAssuredMockMvc.standaloneSetup(this.musicaController);
    }

    @Test
    void deveRetornar200_get(){
        RestAssuredMockMvc.given()
                .when()
                    .get("/api/v1/musicas/")
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

}
