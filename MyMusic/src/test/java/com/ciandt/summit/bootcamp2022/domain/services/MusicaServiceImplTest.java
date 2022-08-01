package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Artista;
import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicaServicePort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicaRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.MusicasNotFoundException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.RuleLengthViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MusicaServiceImplTest {

    @MockBean
    private MusicaRepositoryPort musicaRepositoryPort;

    @Autowired
    private MusicaServicePort musicaServicePort;

    Artista artista1 = new Artista(UUID.randomUUID().toString(), "Bruno Mars");
    Musica musica1 = new Musica(UUID.randomUUID().toString(), "Talking to the moon", artista1);
    Artista artista2 = new Artista(UUID.randomUUID().toString(), "The Beatles");
    Musica musica2 = new Musica(UUID.randomUUID().toString(), "Here Comes the Sun", artista2);
    Artista artista3 = new Artista(UUID.randomUUID().toString(), "Michael Jackson");
    Musica musica3 = new Musica(UUID.randomUUID().toString(), "Billie Jean", artista3);

    @Test
    void deveRetornarMusicasBaseadoNoParametro() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica2));

        String filtro = "Bea";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServicePort.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
    }

    @Test
    void deveRetornarMusicasBaseadoNoParametroMinusculo() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica2));

        String filtro = "bea";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServicePort.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
    }

    @Test
    void deveRetornarMusicasBaseadoNoParametroMaiusculo() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica2));

        String filtro = "BEA";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServicePort.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
    }

    @Test
    void deveLancarExcecaoQuandoParametroForMenorQueDoisCaracteres() throws Exception {
        String filtro = "a";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(RuleLengthViolationException.class, () -> musicaServicePort.findByNameArtistaOrNameMusica(filtro));
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarMusicasBaseadoNoParametro() throws Exception {
        String filtro = "naoExiste";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(new ArrayList<>());

        try {
            musicaServicePort.findByNameArtistaOrNameMusica(filtro);
        } catch (Throwable e) {
            assertEquals(MusicasNotFoundException.class, e.getClass());
        }
    }

    @Test
    void deveRetornarTodasAsMusicas() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica1, musica2, musica3));

        when(musicaRepositoryPort.findAll()).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServicePort.findAll();

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
    }

    @Test
    void deveRetornarListaVazia() throws Exception {
        when(musicaRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        List<MusicaDTO> musicaDTOS = musicaServicePort.findAll();

        assertThat(musicaDTOS).isEmpty();
        assertThat(musicaDTOS.size()).isEqualTo(0);
    }

}
