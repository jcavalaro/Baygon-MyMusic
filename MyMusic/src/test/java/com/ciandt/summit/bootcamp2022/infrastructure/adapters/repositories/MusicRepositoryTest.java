package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Music;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.ArtistEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MusicRepositoryTest {

    @Mock
    MusicJpaRepository musicJpaRepository;

    @InjectMocks
    MusicRepository musicRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    ArtistEntity artist1 = new ArtistEntity(UUID.randomUUID().toString(), "Bruno Mars");
    MusicEntity music1 = new MusicEntity(UUID.randomUUID().toString(), "Talking to the moon", artist1);
    ArtistEntity artist2 = new ArtistEntity(UUID.randomUUID().toString(), "The Beatles");
    MusicEntity music2 = new MusicEntity(UUID.randomUUID().toString(), "Here Comes the Sun", artist2);
    ArtistEntity artist3 = new ArtistEntity(UUID.randomUUID().toString(), "Michael Jackson");
    MusicEntity music3 = new MusicEntity(UUID.randomUUID().toString(), "Billie Jean", artist3);

    @Test
    public void shouldReturnMusicBasedOnParameter() throws Exception {
        List<MusicEntity> musicEntityList = new ArrayList<>(List.of(music2));

        String filter = "Bea";

        when(musicJpaRepository.findByNameArtistOrNameMusic(filter)).thenReturn(musicEntityList);

        List<Music> musics = musicRepository.findByNameArtistOrNameMusic(filter);

        assertNotNull(musics);
        assertEquals(musicEntityList.size(), musics.size());
        assertEquals("The Beatles", musicEntityList.get(0).getArtist().getName());
    }

    @Test
    public void shouldReturnMusicBasedOnParameterLowerCase() throws Exception {
        List<MusicEntity> musicEntityList = new ArrayList<>(List.of(music2));

        String filter = "bea";

        when(musicJpaRepository.findByNameArtistOrNameMusic(filter)).thenReturn(musicEntityList);

        List<Music> musics = musicRepository.findByNameArtistOrNameMusic(filter);

        assertNotNull(musics);
        assertEquals(musicEntityList.size(), musics.size());
        assertEquals("The Beatles", musics.get(0).getArtist().getName());
    }

    @Test
    public void shouldReturnMusicBasedOnParameterUpperCase() throws Exception {
        List<MusicEntity> musicEntityList = new ArrayList<>(List.of(music2));

        String filter = "BEA";

        when(musicJpaRepository.findByNameArtistOrNameMusic(filter)).thenReturn(musicEntityList);

        List<Music> musics = musicRepository.findByNameArtistOrNameMusic(filter);

        assertNotNull(musics);
        assertEquals(musicEntityList.size(), musics.size());
        assertEquals("The Beatles", musics.get(0).getArtist().getName());
    }

    @Test
    public void shouldReturnEmptyListWhenParameterNotInformed() throws Exception {
        when(musicJpaRepository.findByNameArtistOrNameMusic(null)).thenReturn(Collections.emptyList());

        List<Music> musics = musicRepository.findByNameArtistOrNameMusic(null);

        assertThat(musics).isEmpty();
        assertThat(musics.size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnAllMusic() throws Exception {
        List<MusicEntity> musicEntityList = new ArrayList<>(List.of(music1, music2, music3));

        when(musicJpaRepository.findAll()).thenReturn(musicEntityList);

        List<Music> musics = musicRepository.findAll();

        assertNotNull(musics);
        assertEquals(musicEntityList.size(), musics.size());
        assertEquals("Bruno Mars", musics.get(0).getArtist().getName());
        assertEquals("The Beatles", musics.get(1).getArtist().getName());
        assertEquals("Michael Jackson", musics.get(2).getArtist().getName());
    }

    @Test
    public void shouldReturnEmptyList() throws Exception {
        when(musicJpaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Music> musics = musicRepository.findAll();

        assertThat(musics).isEmpty();
        assertThat(musics.size()).isEqualTo(0);
    }

}
