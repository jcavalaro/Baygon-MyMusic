package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.ArtistaEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicaEntity;
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
public class MusicaRepositoryTest {

    @Mock
    MusicaJpaRepository musicaJpaRepository;

    @InjectMocks
    MusicaRepository musicaRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    ArtistaEntity artista1 = new ArtistaEntity(UUID.randomUUID().toString(), "Bruno Mars");
    MusicaEntity musica1 = new MusicaEntity(UUID.randomUUID().toString(), "Talking to the moon", artista1);
    ArtistaEntity artista2 = new ArtistaEntity(UUID.randomUUID().toString(), "The Beatles");
    MusicaEntity musica2 = new MusicaEntity(UUID.randomUUID().toString(), "Here Comes the Sun", artista2);
    ArtistaEntity artista3 = new ArtistaEntity(UUID.randomUUID().toString(), "Michael Jackson");
    MusicaEntity musica3 = new MusicaEntity(UUID.randomUUID().toString(), "Billie Jean", artista3);

    @Test
    public void deveRetornarMusicasBaseadoNoParametro() throws Exception {
        List<MusicaEntity> musicasEntity = new ArrayList<>(List.of(musica2));

        String filtro = "Bea";

        when(musicaJpaRepository.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicasEntity);

        List<Musica> musicas = musicaRepository.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicas);
        assertEquals(musicasEntity.size(), musicas.size());
        assertEquals("The Beatles", musicasEntity.get(0).getArtista().getNome());
    }

    @Test
    public void deveRetornarMusicasBaseadoNoParametroMinusculo() throws Exception {
        List<MusicaEntity> musicasEntity = new ArrayList<>(List.of(musica2));

        String filtro = "bea";

        when(musicaJpaRepository.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicasEntity);

        List<Musica> musicas = musicaRepository.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicas);
        assertEquals(musicasEntity.size(), musicas.size());
        assertEquals("The Beatles", musicas.get(0).getArtista().getNome());
    }

    @Test
    public void deveRetornarMusicasBaseadoNoParametroMaiusculo() throws Exception {
        List<MusicaEntity> musicasEntity = new ArrayList<>(List.of(musica2));

        String filtro = "BEA";

        when(musicaJpaRepository.findByNameArtistaOrNameMusica(filtro)).thenReturn(musicasEntity);

        List<Musica> musicas = musicaRepository.findByNameArtistaOrNameMusica(filtro);

        assertNotNull(musicas);
        assertEquals(musicasEntity.size(), musicas.size());
        assertEquals("The Beatles", musicas.get(0).getArtista().getNome());
    }

    @Test
    public void deveRetornarListaVaziaParametroNaoForInformado() throws Exception {
        when(musicaJpaRepository.findByNameArtistaOrNameMusica(null)).thenReturn(Collections.emptyList());

        List<Musica> musicas = musicaRepository.findByNameArtistaOrNameMusica(null);

        assertThat(musicas).isEmpty();
        assertThat(musicas.size()).isEqualTo(0);
    }

    @Test
    public void deveRetornarTodasAsMusicas() throws Exception {
        List<MusicaEntity> musicasEntity = new ArrayList<>(List.of(musica1, musica2, musica3));

        when(musicaJpaRepository.findAll()).thenReturn(musicasEntity);

        List<Musica> musicas = musicaRepository.findAll();

        assertNotNull(musicas);
        assertEquals(musicasEntity.size(), musicas.size());
        assertEquals("Bruno Mars", musicas.get(0).getArtista().getNome());
        assertEquals("The Beatles", musicas.get(1).getArtista().getNome());
        assertEquals("Michael Jackson", musicas.get(2).getArtista().getNome());
    }

    @Test
    public void deveRetornarListaVazia() throws Exception {
        when(musicaJpaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Musica> musicas = musicaRepository.findAll();

        assertThat(musicas).isEmpty();
        assertThat(musicas.size()).isEqualTo(0);
    }

}
