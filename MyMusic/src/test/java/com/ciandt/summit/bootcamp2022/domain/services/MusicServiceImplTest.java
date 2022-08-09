package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Artist;
import com.ciandt.summit.bootcamp2022.domain.models.Music;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MusicServiceImplTest {

    @Mock
    MusicRepositoryPort musicRepositoryPort;

    @InjectMocks
    MusicServiceImpl musicServiceImpl;

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

    @Test
    public void shouldReturnMusicBasedOnParameter() throws Exception {
        List<Music> musics = new ArrayList<>(List.of(music2));

        String filter = "Bea";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filter)).thenReturn(musics);

        List<MusicDTO> musicsDTO = musicServiceImpl.findByNameArtistOrNameMusic(filter);

        assertNotNull(musicsDTO);
        assertEquals(musics.size(), musicsDTO.size());
        assertEquals("The Beatles", musicsDTO.get(0).getArtist().getName());
    }

    @Test
    public void shouldReturnMusicBasedOnParameterLowerCase() throws Exception {
        List<Music> musics = new ArrayList<>(List.of(music2));

        String filter = "bea";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filter)).thenReturn(musics);

        List<MusicDTO> musicsDTO = musicServiceImpl.findByNameArtistOrNameMusic(filter);

        assertNotNull(musicsDTO);
        assertEquals(musics.size(), musicsDTO.size());
        assertEquals("The Beatles", musicsDTO.get(0).getArtist().getName());
    }

    @Test
    public void shouldReturnMusicBasedOnParameterUpperCase() throws Exception {
        List<Music> musics = new ArrayList<>(List.of(music2));

        String filter = "BEA";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filter)).thenReturn(musics);

        List<MusicDTO> musicsDTO = musicServiceImpl.findByNameArtistOrNameMusic(filter);

        assertNotNull(musicsDTO);
        assertEquals(musics.size(), musicsDTO.size());
        assertEquals("The Beatles", musicsDTO.get(0).getArtist().getName());
    }

    @Test
    public void shouldReturnAllMusicsWhenParameterNotInformed() throws Exception {
        List<Music> musics = new ArrayList<>(List.of(music1, music2, music3));

        when(musicRepositoryPort.findAll()).thenReturn(musics);

        List<MusicDTO> musicsDTO = musicServiceImpl.findByNameArtistOrNameMusic(null);

        assertNotNull(musicsDTO);
        assertEquals(musics.size(), musicsDTO.size());
        assertEquals("Bruno Mars", musicsDTO.get(0).getArtist().getName());
        assertEquals("The Beatles", musicsDTO.get(1).getArtist().getName());
        assertEquals("Michael Jackson", musicsDTO.get(2).getArtist().getName());
    }

    @Test
    public void shouldThrowExceptionWhenParameterIsLessThanTwoCharacters() throws Exception {
        String filter = "a";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filter)).thenReturn(new ArrayList<>());

        try {
            musicServiceImpl.findByNameArtistOrNameMusic(filter);
        } catch (Throwable e) {
            assertEquals(BusinessRuleException.class, e.getClass());
            assertEquals("Filter must be at least 2 characters long.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenParameterIsEqualThanTwoCharacters() throws Exception {
        List<Music> musics = new ArrayList<>(List.of(music2));

        String filter = "be";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filter)).thenReturn(musics);

        List<MusicDTO> musicsDTO = musicServiceImpl.findByNameArtistOrNameMusic(filter);

        assertNotNull(musicsDTO);
        assertEquals(musics.size(), musicsDTO.size());
        assertEquals("The Beatles", musicsDTO.get(0).getArtist().getName());
    }

    @Test
    public void shouldThrowExceptionWhenNotFindingMusicsBasedOnParameter() throws Exception {
        String filter = "naoExiste";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filter)).thenReturn(new ArrayList<>());

        try {
            musicServiceImpl.findByNameArtistOrNameMusic(filter);
        } catch (Throwable e) {
            assertEquals(NotFoundException.class, e.getClass());
        }
    }

    @Test
    public void shouldReturnAllMusic() throws Exception {
        List<Music> musics = new ArrayList<>(List.of(music1, music2, music3));

        when(musicRepositoryPort.findAll()).thenReturn(musics);

        List<MusicDTO> musicsDTO = musicServiceImpl.findAll();

        assertNotNull(musicsDTO);
        assertEquals(musics.size(), musicsDTO.size());
        assertEquals("Bruno Mars", musicsDTO.get(0).getArtist().getName());
        assertEquals("The Beatles", musicsDTO.get(1).getArtist().getName());
        assertEquals("Michael Jackson", musicsDTO.get(2).getArtist().getName());
    }

    @Test
    public void shouldReturnEmptyList() throws Exception {
        when(musicRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        List<MusicDTO> musicsDTO = musicServiceImpl.findAll();

        assertThat(musicsDTO).isEmpty();
    }

    @Test
    public void shouldReturnMusicById() throws Exception {
        String id = "Id Music 3";

        when(musicRepositoryPort.findById(id)).thenReturn(music3);

        MusicDTO musicFound = musicServiceImpl.findById(id);

        assertNotNull(musicFound);
        assertEquals("Id Music 3", musicFound.getId());
        assertEquals("Billie Jean", musicFound.getName());
        assertEquals("Michael Jackson", musicFound.getArtist().getName());
    }

    @Test
    public void shouldThrowExceptionWhenIdNotInformed() throws Exception {
        String id = "";

        try {
            musicServiceImpl.findById(id);
        } catch (Throwable e) {
            assertEquals(BusinessRuleException.class, e.getClass());
            assertEquals("Music Id not informed.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenMusicDoesNotExists() throws Exception {
        String id = "naoExisteMusica";

        when(musicRepositoryPort.findById(id)).thenReturn(null);

        try {
            musicServiceImpl.findById(id);
        } catch (Throwable e) {
            assertEquals(BusinessRuleException.class, e.getClass());
            assertEquals("Music does not exist in the database.", e.getMessage());
        }
    }

}
