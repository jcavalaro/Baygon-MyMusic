package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.ApplicationConfigTest;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicaRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.request;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;

@DisplayName("MusicaServiceImplTest")
public class MusicaServiceImplTest extends ApplicationConfigTest {


    @MockBean
    private MusicaRepositoryPort musicaRepositoryPort;

    @Test
    @DisplayName("deve trazer lista por filtro artista ou musica")
    public void TrazerListaPorFiltro(){

        Mockito.when(musicaRepositoryPort.findByNameArtistaOrNameMusica("asdjasid")).then(request("spo"));

    }



}
