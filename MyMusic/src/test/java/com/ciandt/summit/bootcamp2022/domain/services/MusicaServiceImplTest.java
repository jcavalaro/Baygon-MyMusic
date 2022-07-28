package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.ApplicationConfigTest;
import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistaDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicaRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.request;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;

@DisplayName("MusicaServiceImplTest")
public class MusicaServiceImplTest extends ApplicationConfigTest {


    @MockBean
    private MusicaRepositoryPort musicaRepositoryPort;

    @Test
    @DisplayName("deve trazer lista por filtro artista ou musica")
    public void TrazerListaPorFiltro(){

        List<Musica> list = Collections.singletonList(Mockito.mock(Musica.class));

        String filter = "u2";
        Mockito.when(musicaRepositoryPort.findByNameArtistaOrNameMusica(ArgumentMatchers.eq(filter))).thenReturn(list);
    }

    @Test
    @DisplayName("deve trazer todas as musicas")
    public void TrazerTodasAsMusicas() {



    }
}
