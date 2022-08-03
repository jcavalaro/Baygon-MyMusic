package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicServicePort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.NotFoundException;
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
public class MusicControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    MusicServicePort musicServicePort;

    @InjectMocks
    MusicController musicController;

    ArtistDTO artist1 = new ArtistDTO(UUID.randomUUID().toString(), "Bruno Mars");
    MusicDTO music1 = new MusicDTO(UUID.randomUUID().toString(), "Talking to the moon", artist1);
    ArtistDTO artist2 = new ArtistDTO(UUID.randomUUID().toString(), "The Beatles");
    MusicDTO music2 = new MusicDTO(UUID.randomUUID().toString(), "Here Comes the Sun", artist2);
    ArtistDTO artist3 = new ArtistDTO(UUID.randomUUID().toString(), "Michael Jackson");
    MusicDTO music3 = new MusicDTO(UUID.randomUUID().toString(), "Billie Jean", artist3);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(musicController).setControllerAdvice(new ExceptionService()).build();
    }

    @Test
    public void shouldReturn200WithFilter() throws Exception {
        List<MusicDTO> musics = new ArrayList<>(Arrays.asList(music2));

        when(musicServicePort.findByNameArtistOrNameMusic("Beatles")).thenReturn(musics);

        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/musicas")
            .param("filter", "Beatles")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].name", is("Here Comes the Sun")));
    }

    @Test
    public void shouldReturn200WithoutFilter() throws Exception {
        List<MusicDTO> musics = new ArrayList<>(Arrays.asList(music1, music2, music3));

        when(musicServicePort.findByNameArtistOrNameMusic(null)).thenReturn(musics);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/musicas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].name", is("Talking to the moon")))
                .andExpect(jsonPath("$.data[1].name", is("Here Comes the Sun")))
                .andExpect(jsonPath("$.data[2].name", is("Billie Jean")));
    }

    @Test
    public void shouldReturn200WhenFilterIsLowerCase() throws Exception {
        List<MusicDTO> musics = new ArrayList<>(Arrays.asList(music2));

        when(musicServicePort.findByNameArtistOrNameMusic("beatles")).thenReturn(musics);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/musicas")
                        .param("filter", "beatles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Here Comes the Sun")));
    }

    @Test
    public void shouldReturn200WhenFilterIsUpperCase() throws Exception {
        List<MusicDTO> musics = new ArrayList<>(Arrays.asList(music2));

        when(musicServicePort.findByNameArtistOrNameMusic("BEATLES")).thenReturn(musics);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/musicas")
                        .param("filter", "BEATLES")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Here Comes the Sun")));
    }

    @Test
    public void shouldReturn400ErrorWhenFilterIsLessThanTwoCharacters() throws Exception {
        when(musicServicePort.findByNameArtistOrNameMusic("a")).thenThrow(new BusinessRuleException("Mensagem de exceção"));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/musicas")
                .param("filter", "a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Mensagem de exceção")));
    }

    @Test
    public void shouldReturnError204WhenFilterDoesNotResult() throws Exception {
        when(musicServicePort.findByNameArtistOrNameMusic("naoExiste")).thenThrow(new NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/musicas")
                .param("filter", "naoExiste")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
