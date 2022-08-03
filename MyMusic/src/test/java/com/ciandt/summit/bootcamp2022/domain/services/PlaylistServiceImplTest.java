package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Artist;
import com.ciandt.summit.bootcamp2022.domain.models.Music;
import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.PlaylistRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.PlaylistEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PlaylistServiceImplTest {

    @Mock
    PlaylistRepositoryPort playlistRepositoryPort;

    @Mock
    MusicRepositoryPort musicRepositoryPort;

    @InjectMocks
    PlaylistServiceImpl playlistServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Artist artist1 = new Artist("Id Artist 1", "Bruno Mars");
    Music music1 = new Music("Id Music 1", "Talking to the moon", artist1);
    Artist artist2 = new Artist("Id Artist 2", "The Beatles");
    Music music2 = new Music("Id Music 2", "Here Comes the Sun", artist2);
    Artist artist3 = new Artist("Id Artist 3", "Michael Jackson");
    Music music3 = new Music("Id Music 3", "Billie Jean", artist3);

    List<Music> musics1 = new ArrayList<>(List.of(music1));

    List<Music> musics2 = new ArrayList<>(List.of(music1, music2, music3));

    Playlist playlistEmpty = new Playlist("Id Playlist Empty", new ArrayList<>());
    Playlist playlistWithOneMusic = new Playlist("Id Playlist With One Music", musics1);
    PlaylistEntity playlistEntityEmpty = playlistEmpty.toPlaylistEntity();
    PlaylistEntity playlistEntityWithOneMusic = playlistWithOneMusic.toPlaylistEntity();

    Playlist playlistUpdatedWithOneMusic = new Playlist("Id Playlist Updated With One Music", musics1);
    Playlist playlistUpdatedWithThreeMusic = new Playlist("Id Playlist Updated With Three Music", musics2);

    List<MusicDTO> musicsDTOWithOneMusic = musics1.stream().map(Music::toMusicDTO).collect(Collectors.toList());
    List<MusicDTO> musicsDTOWithThreeMusic = musics2.stream().map(Music::toMusicDTO).collect(Collectors.toList());

    DataDTO dataDTOWithOneMusic = new DataDTO(musicsDTOWithOneMusic);
    DataDTO dataDTOWithThreeMusic = new DataDTO(musicsDTOWithThreeMusic);

    List<MusicEntity> musicsEntityWithOneMusic = dataDTOWithOneMusic.getData().stream().map(MusicDTO::toMusicEntity).collect(Collectors.toList());
    List<MusicEntity> musicsEntityWithThreeMusic = dataDTOWithThreeMusic.getData().stream().map(MusicDTO::toMusicEntity).collect(Collectors.toList());

    @Test
    public void shouldReturnAllPlaylist() throws Exception {
        List<Playlist> playlists = new ArrayList<>(List.of(playlistEmpty, playlistWithOneMusic));

        when(playlistRepositoryPort.findAll()).thenReturn(playlists);

        List<PlaylistDTO> playlistsDTO = playlistServiceImpl.findAll();

        assertNotNull(playlistsDTO);
        assertEquals(playlists.size(), playlistsDTO.size());
        assertEquals("Id Playlist Empty", playlistsDTO.get(0).getId());
        assertEquals("Id Playlist With One Music", playlistsDTO.get(1).getId());
    }

    @Test
    public void shouldReturnEmptyList() throws Exception {
        when(playlistRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        List<PlaylistDTO> playlistsDTO = playlistServiceImpl.findAll();

        assertThat(playlistsDTO).isEmpty();
        assertThat(playlistsDTO.size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnPlaylistById() throws Exception {
        String id = "Id Playlist With One Music";

        when(playlistRepositoryPort.findById(id)).thenReturn(playlistWithOneMusic);

        PlaylistDTO playlistFound = playlistServiceImpl.findById(id);

        assertNotNull(playlistFound);
        assertEquals("Id Playlist With One Music", playlistFound.getId());
        assertEquals("Id Music 1", playlistFound.getMusics().get(0).getId());
    }

    @Test
    public void shouldThrowExceptionWhenIdNotInformed() throws Exception {
        String id = "";

        try {
            playlistServiceImpl.findById(id);
        } catch (Throwable e) {
            assertEquals(BusinessRuleException.class, e.getClass());
            assertEquals("Playlist Id not informed.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenPlaylistDoesNotExists() throws Exception {
        String id = "naoExistePlaylist";

        when(playlistRepositoryPort.findById(id)).thenReturn(null);

        try {
            playlistServiceImpl.findById(id);
        } catch (Throwable e) {
            assertEquals(BusinessRuleException.class, e.getClass());
            assertEquals("Playlist does not exist in the database.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenPlaylistIdNotInformed() throws Exception {
        String playlistId = "";

        try {
            playlistServiceImpl.addMusicsToPlaylist(playlistId, dataDTOWithOneMusic);
        } catch (Throwable e) {
            assertEquals(BusinessRuleException.class, e.getClass());
            assertEquals("Playlist Id not informed.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenPlaylistIdDoesNotExists() throws Exception {
        String playlistId = "Id Playlist Does Not Exists";

        when(playlistRepositoryPort.findById(playlistId)).thenReturn(null);

        try {
            playlistServiceImpl.addMusicsToPlaylist(playlistId, dataDTOWithOneMusic);
        } catch (Throwable e) {
            assertEquals(BusinessRuleException.class, e.getClass());
            assertEquals("Playlist does not exist in the database.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenMusicIdDoesNotExists() throws Exception {
        String musicId = "Id Music Does Not Exists";

        when(playlistRepositoryPort.findById(playlistEmpty.getId())).thenReturn(playlistEmpty);
        when(musicRepositoryPort.findById(musicId)).thenReturn(null);

        try {
            playlistServiceImpl.addMusicsToPlaylist(playlistEmpty.getId(), dataDTOWithOneMusic);
        } catch (Throwable e) {
            assertEquals(BusinessRuleException.class, e.getClass());
            assertEquals("Music does not exist in the database.", e.getMessage());
        }
    }

    @Test
    public void shouldAddASongToThePlaylist() throws Exception {
        String id = "Id Playlist Empty";

        when(playlistRepositoryPort.findById(id)).thenReturn(playlistEmpty);
        when(musicRepositoryPort.findById(musicsEntityWithOneMusic.get(0).getId())).thenReturn(music1);
        when(playlistRepositoryPort.addMusicsToPlaylist(playlistEntityEmpty)).thenReturn(playlistUpdatedWithOneMusic);

        PlaylistDTO playlistWithNewMusics = playlistServiceImpl.addMusicsToPlaylist(id, dataDTOWithOneMusic);

        assertNotNull(playlistWithNewMusics);
        assertEquals("Id Playlist Updated With One Music", playlistWithNewMusics.getId());
        assertEquals(1, playlistWithNewMusics.getMusics().size());
        assertEquals("Id Music 1", playlistWithNewMusics.getMusics().get(0).getId());
    }

    @Test
    public void shouldAddAListOfSongsToThePlaylist() throws Exception {
        String id = "Id Playlist Empty";

        when(playlistRepositoryPort.findById(id)).thenReturn(playlistEmpty);
        when(musicRepositoryPort.findById(musicsEntityWithThreeMusic.get(0).getId())).thenReturn(music1);
        when(musicRepositoryPort.findById(musicsEntityWithThreeMusic.get(1).getId())).thenReturn(music2);
        when(musicRepositoryPort.findById(musicsEntityWithThreeMusic.get(2).getId())).thenReturn(music3);
        when(playlistRepositoryPort.addMusicsToPlaylist(playlistEntityEmpty)).thenReturn(playlistUpdatedWithThreeMusic);

        PlaylistDTO playlistWithNewMusics = playlistServiceImpl.addMusicsToPlaylist(id, dataDTOWithThreeMusic);

        assertNotNull(playlistWithNewMusics);
        assertEquals("Id Playlist Updated With Three Music", playlistWithNewMusics.getId());
        assertEquals(3, playlistWithNewMusics.getMusics().size());
        assertEquals("Id Music 1", playlistWithNewMusics.getMusics().get(0).getId());
        assertEquals("Id Music 2", playlistWithNewMusics.getMusics().get(1).getId());
        assertEquals("Id Music 3", playlistWithNewMusics.getMusics().get(2).getId());
    }

    @Test
    public void shouldNotAddASongThatAlreadyExistsInThePlaylist() throws Exception {
        String id = "Id Playlist With One Music";

        when(playlistRepositoryPort.findById(id)).thenReturn(playlistWithOneMusic);
        when(musicRepositoryPort.findById(musicsEntityWithThreeMusic.get(0).getId())).thenReturn(music1);
        when(musicRepositoryPort.findById(musicsEntityWithThreeMusic.get(1).getId())).thenReturn(music2);
        when(musicRepositoryPort.findById(musicsEntityWithThreeMusic.get(2).getId())).thenReturn(music3);
        when(playlistRepositoryPort.addMusicsToPlaylist(playlistEntityWithOneMusic)).thenReturn(playlistUpdatedWithThreeMusic);

        PlaylistDTO playlistWithNewMusics = playlistServiceImpl.addMusicsToPlaylist(id, dataDTOWithThreeMusic);

        assertNotNull(playlistWithNewMusics);
        assertEquals("Id Playlist Updated With Three Music", playlistWithNewMusics.getId());
        assertEquals(3, playlistWithNewMusics.getMusics().size());
        assertEquals("Id Music 1", playlistWithNewMusics.getMusics().get(0).getId());
        assertEquals("Id Music 2", playlistWithNewMusics.getMusics().get(1).getId());
        assertEquals("Id Music 3", playlistWithNewMusics.getMusics().get(2).getId());
    }

}
