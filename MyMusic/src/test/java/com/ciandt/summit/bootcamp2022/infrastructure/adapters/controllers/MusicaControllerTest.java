package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistaDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicaServicePort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.MusicasNotFoundException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.RuleLengthViolationException;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions.ExceptionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MusicaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    MusicaServicePort musicaServicePort;

    @InjectMocks
    MusicaController musicaController;

    ArtistaDTO artista1 = new ArtistaDTO(UUID.randomUUID().toString(), "Bruno Mars");
    MusicaDTO musica1 = new MusicaDTO(UUID.randomUUID().toString(), "Talking to the moon", artista1);
    ArtistaDTO artista2 = new ArtistaDTO(UUID.randomUUID().toString(), "The Beatles");
    MusicaDTO musica2 = new MusicaDTO(UUID.randomUUID().toString(), "Here Comes the Sun", artista2);
    ArtistaDTO artista3 = new ArtistaDTO(UUID.randomUUID().toString(), "Michael Jackson");
    MusicaDTO musica3 = new MusicaDTO(UUID.randomUUID().toString(), "Billie Jean", artista3);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(musicaController).setControllerAdvice(new ExceptionService()).build();
    }

    @Test
    public void deveRetornar200ComFiltro() throws Exception {
        List<MusicaDTO> musicas = new ArrayList<>(Arrays.asList(musica2));

        when(musicaServicePort.findByNameArtistaOrNameMusica("Beatles")).thenReturn(musicas);

        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/musicas")
            .param("filtro", "Beatles")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].nome", is("Here Comes the Sun")));
    }

    @Test
    public void deveRetornar200SemFiltro() throws Exception {
        List<MusicaDTO> musicas = new ArrayList<>(Arrays.asList(musica1, musica2, musica3));

        when(musicaServicePort.findByNameArtistaOrNameMusica(null)).thenReturn(musicas);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/musicas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].nome", is("Talking to the moon")))
                .andExpect(jsonPath("$.data[1].nome", is("Here Comes the Sun")))
                .andExpect(jsonPath("$.data[2].nome", is("Billie Jean")));
    }

    @Test
    public void deveRetornar200QuandoOFiltroForMinusculo() throws Exception {
        List<MusicaDTO> musicas = new ArrayList<>(Arrays.asList(musica2));

        when(musicaServicePort.findByNameArtistaOrNameMusica("beatles")).thenReturn(musicas);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/musicas")
                        .param("filtro", "beatles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].nome", is("Here Comes the Sun")));
    }

    @Test
    public void deveRetornar200QuandoOFiltroForMaiusculo() throws Exception {
        List<MusicaDTO> musicas = new ArrayList<>(Arrays.asList(musica2));

        when(musicaServicePort.findByNameArtistaOrNameMusica("BEATLES")).thenReturn(musicas);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/musicas")
                        .param("filtro", "BEATLES")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].nome", is("Here Comes the Sun")));
    }

    @Test
    public void deveRetornarErro400QuandoFiltroForMenorQue2Caracteres() throws Exception {
        List<MusicaDTO> musicas = new ArrayList<>();
        when(musicaServicePort.findByNameArtistaOrNameMusica("a")).thenThrow(new RuleLengthViolationException("Mensagem de exceção"));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/musicas")
                .param("filtro", "a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Mensagem de exceção")));
    }

    @Test
    public void deveRetornarErro204QuandoFiltroNaoApresentarResultado() throws Exception {
        when(musicaServicePort.findByNameArtistaOrNameMusica("naoExiste")).thenThrow(new MusicasNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/musicas")
                .param("filtro", "naoExiste")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
