package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Artist;
import com.ciandt.summit.bootcamp2022.domain.models.Music;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicServicePort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MusicServiceImplTest {

    @MockBean
    private MusicRepositoryPort musicRepositoryPort;

    @Autowired
    private MusicServicePort musicServicePort;

    Artist artist1 = new Artist(UUID.randomUUID().toString(), "Bruno Mars");
    Music music1 = new Music(UUID.randomUUID().toString(), "Talking to the moon", artist1);
    Artist artist2 = new Artist(UUID.randomUUID().toString(), "The Beatles");
    Music music2 = new Music(UUID.randomUUID().toString(), "Here Comes the Sun", artist2);
    Artist artist3 = new Artist(UUID.randomUUID().toString(), "Michael Jackson");
    Music music3 = new Music(UUID.randomUUID().toString(), "Billie Jean", artist3);

    @Test
    void deveRetornarMusicasBaseadoNoParametro() throws Exception {
        List<Music> music = new ArrayList<>(List.of(music2));

        String filtro = "Bea";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filtro)).thenReturn(music);

        List<MusicDTO> musicDTOS = musicServicePort.findByNameArtistOrNameMusic(filtro);

        assertNotNull(musicDTOS);
        assertEquals(music.size(), musicDTOS.size());
    }

    @Test
    void deveRetornarMusicasBaseadoNoParametroMinusculo() throws Exception {
        List<Music> music = new ArrayList<>(List.of(music2));

        String filtro = "bea";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filtro)).thenReturn(music);

        List<MusicDTO> musicDTOS = musicServicePort.findByNameArtistOrNameMusic(filtro);

        assertNotNull(musicDTOS);
        assertEquals(music.size(), musicDTOS.size());
    }

    @Test
    void deveRetornarMusicasBaseadoNoParametroMaiusculo() throws Exception {
        List<Music> music = new ArrayList<>(List.of(music2));

        String filtro = "BEA";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filtro)).thenReturn(music);

        List<MusicDTO> musicDTOS = musicServicePort.findByNameArtistOrNameMusic(filtro);

        assertNotNull(musicDTOS);
        assertEquals(music.size(), musicDTOS.size());
    }

    @Test
    void deveLancarExcecaoQuandoParametroForMenorQueDoisCaracteres() throws Exception {
        String filtro = "a";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filtro)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(BusinessRuleException.class, () -> musicServicePort.findByNameArtistOrNameMusic(filtro));
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarMusicasBaseadoNoParametro() throws Exception {
        String filtro = "naoExiste";

        when(musicRepositoryPort.findByNameArtistOrNameMusic(filtro)).thenReturn(new ArrayList<>());

        try {
            musicServicePort.findByNameArtistOrNameMusic(filtro);
        } catch (Throwable e) {
            assertEquals(NotFoundException.class, e.getClass());
        }
    }

    @Test
    void deveRetornarTodasAsMusicas() throws Exception {
        List<Music> music = new ArrayList<>(List.of(music1, music2, music3));

        when(musicRepositoryPort.findAll()).thenReturn(music);

        List<MusicDTO> musicDTOS = musicServicePort.findAll();

        assertNotNull(musicDTOS);
        assertEquals(music.size(), musicDTOS.size());
    }

    @Test
    void deveRetornarListaVazia() throws Exception {
        when(musicRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        List<MusicDTO> musicDTOS = musicServicePort.findAll();

        assertThat(musicDTOS).isEmpty();
        assertThat(musicDTOS.size()).isEqualTo(0);
    }

}
