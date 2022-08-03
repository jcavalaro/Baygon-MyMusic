package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domain.services.MusicServiceImpl;
import com.ciandt.summit.bootcamp2022.domain.services.PlaylistServiceImpl;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.NotFoundException;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions.ExceptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @Mock
    PlaylistServiceImpl playlistService;

    @InjectMocks
    private PlaylistController playlistController;

    final String id = "a39926f4-6acb-4497-884f-d4e5296ef652";

    ArtistDTO artist1 = new ArtistDTO(UUID.randomUUID().toString(), "Bruno Mars");
    MusicDTO music1 = new MusicDTO(UUID.randomUUID().toString(), "Talking to the moon", artist1);
    ArtistDTO artist2 = new ArtistDTO(UUID.randomUUID().toString(), "The Beatles");
    MusicDTO music2 = new MusicDTO(UUID.randomUUID().toString(), "Here Comes the Sun", artist2);
    ArtistDTO artist3 = new ArtistDTO(UUID.randomUUID().toString(), "Michael Jackson");
    MusicDTO music3 = new MusicDTO(UUID.randomUUID().toString(), "Billie Jean", artist3);

    DataDTO dataDTORequest = new DataDTO(new ArrayList<>(Arrays.asList(music1,music2,music3)));

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(playlistController).setControllerAdvice(new ExceptionService()).build();
    }

    @Test
    public void mustReturn200WhenListMusicIsAdd() throws Exception {
        PlaylistDTO playlistDTO = new PlaylistDTO(id,new ArrayList<>(Arrays.asList(music1,music2,music3)));

        when(playlistService.addMusicsToPlaylist(id, dataDTORequest)).thenReturn(playlistDTO);

        String playlistId = id;
        String uri = "/api/v1/playlists/{playlistId}/musicas";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri,"playlistId")
                        .param("playlistId",playlistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }


}
