package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.NotFoundException;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions.ExceptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
public class PlaylistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    PlaylistServicePort playlistServicePort;

    @InjectMocks
    private PlaylistController playlistController;

    final String id = "942acd1b-e2a4-42e4-bb9c-1226d28d5e53";
    ArtistDTO artist1 = new ArtistDTO(UUID.randomUUID().toString(), "Bruno Mars");
    MusicDTO music1 = new MusicDTO(UUID.randomUUID().toString(), "Talking to the moon", artist1);
    ArtistDTO artist2 = new ArtistDTO(UUID.randomUUID().toString(), "The Beatles");
    MusicDTO music2 = new MusicDTO(UUID.randomUUID().toString(), "Here Comes the Sun", artist2);
    ArtistDTO artist3 = new ArtistDTO(UUID.randomUUID().toString(), "Michael Jackson");
    MusicDTO music3 = new MusicDTO(UUID.randomUUID().toString(), "Billie Jean", artist3);
    ArtistDTO artist4 = new ArtistDTO(UUID.randomUUID().toString(), "Mumford and Sons");
    MusicDTO music4 = new MusicDTO(UUID.randomUUID().toString(), "Guiding Light", artist4);
    MusicDTO music5 = new MusicDTO(UUID.randomUUID().toString(), " ", artist4);
    DataDTO dataDTORequest = new DataDTO(new ArrayList<>(Arrays.asList(music1, music2, music3)));
    DataDTO dataDTORequestError = new DataDTO(new ArrayList<>(Arrays.asList(music4)));
    DataDTO dataPayloadBodyError = new DataDTO(new ArrayList<>(Arrays.asList(music5)));

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(playlistController).setControllerAdvice(new ExceptionService()).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void mustReturn200WhenListMusicIsAdd() throws Exception {
        PlaylistDTO playlistDTO = new PlaylistDTO(id, new ArrayList<>(Arrays.asList(music1, music2, music3)));

        when(playlistServicePort.addMusicsToPlaylist(Mockito.anyString(), Mockito.any(DataDTO.class))).thenReturn(playlistDTO);

        String playlistId = id;
        String uri = "/api/v1/playlists/{playlistId}/musicas";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri, playlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dataDTORequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(id)))
                .andExpect(jsonPath("$.musics[0].name", is("Talking to the moon")));
    }

    @Test
    public void mustReturn400WhenPlaylistDoesntExists() throws Exception {
        when(playlistServicePort.addMusicsToPlaylist(Mockito.anyString(), Mockito.any(DataDTO.class))).thenThrow(new BusinessRuleException("Playlist not found!"));

        String playlistId = id;
        String uri = "/api/v1/playlists/{playlistId}/musicas";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri, playlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dataDTORequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Playlist not found!")));
    }

    @Test
    public void mustReturn400WhenTheMusicDoesNotExist() throws Exception {
        PlaylistDTO playlistDTO = new PlaylistDTO(id, new ArrayList<>(Arrays.asList(music4)));

        when(playlistServicePort.addMusicsToPlaylist(Mockito.anyString(), Mockito.any(DataDTO.class))).thenThrow(new BusinessRuleException("Music Does Not Exist in the Base."));

        String playlistId = id;
        String uri = "/api/v1/playlists/{playlistId}/musicas";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri, id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dataDTORequestError)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void mustReturn400WhenPayloadBodyDoesNotConformToDocumentation() throws Exception {
        PlaylistDTO playlistDTO = new PlaylistDTO(id, new ArrayList<>(Arrays.asList(music5)));

        when(playlistServicePort.addMusicsToPlaylist(Mockito.anyString(), Mockito.any(DataDTO.class))).thenThrow(new BusinessRuleException("Payload Body Does Not Conform to Documentation."));

        String playlistId = id;
        String uri = "/api/v1/playlists/{playlistId}/musicas";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri, id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dataDTORequestError)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void mustReturn200WhenMusicRemove() throws Exception {

        PlaylistDTO playlistDTO = new PlaylistDTO(id, new ArrayList<>(Arrays.asList(music1, music2, music3)));

        when(playlistServicePort.removeMusicFromPlaylist(Mockito.anyString(), Mockito.anyString())).thenReturn(playlistDTO);

        String uri = "/api/v1/playlists/{playlistId}/musicas/{musicaId}";

        String playlistId = id;
        String musicaId = music1.getId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri, id, musicaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.musics", hasSize(3)));
    }

    @Test
    public void shouldReturn200WithUserName() throws Exception {
        PlaylistDTO playlistDTO = new PlaylistDTO(id, new ArrayList<>(List.of(music1, music2, music3)));
        List<PlaylistDTO> playlistsDTO = new ArrayList<>(List.of(playlistDTO));

        when(playlistServicePort.findByUserName(Mockito.anyString())).thenReturn(playlistsDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/playlists")
                        .param("user", "Mariana")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].musics", hasSize(3)));
    }

    @Test
    public void shouldReturn200WhenFilterIsLowerCase() throws Exception {
        PlaylistDTO playlistDTO = new PlaylistDTO(id, new ArrayList<>(List.of(music1, music2, music3)));
        List<PlaylistDTO> playlistsDTO = new ArrayList<>(List.of(playlistDTO));

        when(playlistServicePort.findByUserName(Mockito.anyString())).thenReturn(playlistsDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/playlists")
                        .param("user", "mariana")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].musics", hasSize(3)));
    }

    @Test
    public void shouldReturn200WhenFilterIsUpperCase() throws Exception {
        PlaylistDTO playlistDTO = new PlaylistDTO(id, new ArrayList<>(List.of(music1, music2, music3)));
        List<PlaylistDTO> playlistsDTO = new ArrayList<>(List.of(playlistDTO));

        when(playlistServicePort.findByUserName(Mockito.anyString())).thenReturn(playlistsDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/playlists")
                        .param("user", "MARIANA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].musics", hasSize(3)));
    }

    @Test
    public void shouldReturn400WithoutUserName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/playlists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Parameter is Required")));
    }

    @Test
    public void shouldReturnError204WhenUserNameDoesNotResult() throws Exception {
        when(playlistServicePort.findByUserName(Mockito.anyString())).thenThrow(new NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/playlists")
                .param("user", "naoExiste")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
