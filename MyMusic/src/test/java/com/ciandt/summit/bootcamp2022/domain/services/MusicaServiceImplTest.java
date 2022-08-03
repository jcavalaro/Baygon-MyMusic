package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Artista;
import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicaRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.MusicasNotFoundException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.RuleLengthViolationException;
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
public class MusicaServiceImplTest {

    @Mock
    MusicaRepositoryPort musicaRepositoryPort;

    @InjectMocks
    MusicaServiceImpl musicaServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Artista artista1 = new Artista(UUID.randomUUID().toString(), "Bruno Mars");
    Musica musica1 = new Musica(UUID.randomUUID().toString(), "Talking to the moon", artista1);
    Artista artista2 = new Artista(UUID.randomUUID().toString(), "The Beatles");
    Musica musica2 = new Musica(UUID.randomUUID().toString(), "Here Comes the Sun", artista2);
    Artista artista3 = new Artista(UUID.randomUUID().toString(), "Michael Jackson");
    Musica musica3 = new Musica(UUID.randomUUID().toString(), "Billie Jean", artista3);

    @Test
    public void deveRetornarMusicasBaseadoNoParametro() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica2));

        String filtro = "Bea";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServiceImpl.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
        assertEquals("The Beatles", musicaDTOS.get(0).getArtista().getNome());
    }

    @Test
    public void deveRetornarMusicasBaseadoNoParametroMinusculo() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica2));

        String filtro = "bea";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServiceImpl.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
        assertEquals("The Beatles", musicaDTOS.get(0).getArtista().getNome());
    }

    @Test
    public void deveRetornarMusicasBaseadoNoParametroMaiusculo() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica2));

        String filtro = "BEA";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServiceImpl.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
        assertEquals("The Beatles", musicaDTOS.get(0).getArtista().getNome());
    }

    @Test
    public void deveRetornarTodasAsMusicasQuandoParametroNaoForInformado() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica1, musica2, musica3));

        when(musicaRepositoryPort.findAll()).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServiceImpl.findByNameArtistaOrNameMusica(null);

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
        assertEquals("Bruno Mars", musicaDTOS.get(0).getArtista().getNome());
        assertEquals("The Beatles", musicaDTOS.get(1).getArtista().getNome());
        assertEquals("Michael Jackson", musicaDTOS.get(2).getArtista().getNome());
    }

    @Test
    public void deveLancarExcecaoQuandoParametroForMenorQueDoisCaracteres() throws Exception {
        String filtro = "a";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(new ArrayList<>());

        assertThrows(RuleLengthViolationException.class, () -> musicaServiceImpl.findByNameArtistaOrNameMusica(filtro));
    }

    @Test
    public void deveLancarExcecaoQuandoNaoEncontrarMusicasBaseadoNoParametro() throws Exception {
        String filtro = "naoExiste";

        when(musicaRepositoryPort.findByNameArtistaOrNameMusica(filtro)).thenReturn(new ArrayList<>());

        try {
            musicaServiceImpl.findByNameArtistaOrNameMusica(filtro);
        } catch (Throwable e) {
            assertEquals(MusicasNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void deveRetornarTodasAsMusicas() throws Exception {
        List<Musica> musicas = new ArrayList<>(List.of(musica1, musica2, musica3));

        when(musicaRepositoryPort.findAll()).thenReturn(musicas);

        List<MusicaDTO> musicaDTOS = musicaServiceImpl.findAll();

        assertNotNull(musicaDTOS);
        assertEquals(musicas.size(), musicaDTOS.size());
        assertEquals("Bruno Mars", musicaDTOS.get(0).getArtista().getNome());
        assertEquals("The Beatles", musicaDTOS.get(1).getArtista().getNome());
        assertEquals("Michael Jackson", musicaDTOS.get(2).getArtista().getNome());
    }

    @Test
    public void deveRetornarListaVazia() throws Exception {
        when(musicaRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        List<MusicaDTO> musicaDTOS = musicaServiceImpl.findAll();

        assertThat(musicaDTOS).isEmpty();
        assertThat(musicaDTOS.size()).isEqualTo(0);
    }

}
