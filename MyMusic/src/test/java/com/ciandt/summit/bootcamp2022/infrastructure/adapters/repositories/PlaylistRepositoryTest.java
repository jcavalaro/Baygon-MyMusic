package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.ArtistEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.PlaylistEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PlaylistRepositoryTest {

    @Mock
    PlaylistJpaRepository playlistJpaRepository;

    @Mock
    UserJpaRepository userJpaRepository;

    @InjectMocks
    PlaylistRepository playlistRepository;

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
    List<MusicEntity> musics1 = List.of(music2, music3);
    PlaylistEntity playlistEntity1 = new PlaylistEntity("My Playlist 1", musics1);
    List<MusicEntity> musics2 = List.of(music2);
    PlaylistEntity playlistEntity2 = new PlaylistEntity("My Playlist 2", musics2);

    UserEntity userEntity = new UserEntity("Id User 1", "Mariana", playlistEntity1);

    @Test
    public void findAllSucess() throws Exception {
        List<PlaylistEntity> playlistEntityTest = List.of(playlistEntity1, playlistEntity2);

        when(playlistJpaRepository.findAll()).thenReturn(playlistEntityTest);
        List<Playlist> playlist = playlistRepository.findAll();

        assertNotNull(playlist);
        assertEquals(playlistEntityTest.size(), playlist.size());
        assertEquals("My Playlist 1", playlist.get(0).getId());
        assertEquals("My Playlist 2", playlist.get(1).getId());
    }

    @Test
    public void findByIdSucess() throws Exception {
        Optional<PlaylistEntity> playlistEntityTest = Optional.of(playlistEntity1);
        String id = "My Playlist 1";

        when(playlistJpaRepository.findById(id)).thenReturn(playlistEntityTest);
        Optional<Playlist> playlist = Optional.ofNullable(playlistRepository.findById(id));

        assertNotNull(playlist);
        assertEquals(playlistEntityTest.get().getId(), playlist.get().getId());
        assertEquals("My Playlist 1", playlistEntityTest.get().getId());
    }

    @Test
    public void findByIdFailure() throws Exception {
        Optional<PlaylistEntity> playlistEntityTest = Optional.empty();
        String id = null;

        when(playlistJpaRepository.findById(id)).thenReturn(playlistEntityTest);
        Optional<Playlist> playlist = Optional.ofNullable(playlistRepository.findById(id));

        assertTrue(playlist.isEmpty());
    }

    @Test
    public void addMusicsToPlaylistSucess() {
        when(playlistJpaRepository.save(playlistEntity1)).thenReturn(playlistEntity1);
        Playlist playlistUpdated = playlistRepository.addMusicsToPlaylist(playlistEntity1);
        Mockito.verify(playlistJpaRepository, Mockito.times(1))
                .save(playlistEntity1);
        assertNotNull(playlistUpdated);
    }

    @Test
    public void removeMusicFromPlaylistSucess() {
        when(playlistJpaRepository.save(playlistEntity1)).thenReturn(playlistEntity1);
        Playlist playlistUpdated = playlistRepository.removeMusicFromPlaylist(playlistEntity1);
        Mockito.verify(playlistJpaRepository, Mockito.times(1))
                .save(playlistEntity1);
        assertNotNull(playlistUpdated);
    }

    @Test
    public void shouldReturnPlaylistBasedOnParameter() throws Exception {
        List<UserEntity> userEntityList = new ArrayList<>(List.of(userEntity));

        String userName = "Mariana";

        when(userJpaRepository.findByNameIgnoreCase(userName)).thenReturn(userEntityList);

        Optional<PlaylistEntity> playlistEntity = Optional.of(playlistEntity1);
        String id = "My Playlist 1";

        when(playlistJpaRepository.findById(id)).thenReturn(playlistEntity);

        List<Playlist> playlists = playlistRepository.findByUserName(userName);

        assertNotNull(playlists);
        assertEquals(1, playlists.size());
        assertEquals(id, playlists.get(0).getId());
    }

    @Test
    public void shouldReturnPlaylistBasedOnParameterLowerCase() throws Exception {
        List<UserEntity> userEntityList = new ArrayList<>(List.of(userEntity));

        String userName = "mariana";

        when(userJpaRepository.findByNameIgnoreCase(userName)).thenReturn(userEntityList);

        Optional<PlaylistEntity> playlistEntity = Optional.of(playlistEntity1);
        String id = "My Playlist 1";

        when(playlistJpaRepository.findById(id)).thenReturn(playlistEntity);

        List<Playlist> playlists = playlistRepository.findByUserName(userName);

        assertNotNull(playlists);
        assertEquals(1, playlists.size());
        assertEquals(id, playlists.get(0).getId());
    }

    @Test
    public void shouldReturnPlaylistBasedOnParameterUpperCase() throws Exception {
        List<UserEntity> userEntityList = new ArrayList<>(List.of(userEntity));

        String userName = "MARIANA";

        when(userJpaRepository.findByNameIgnoreCase(userName)).thenReturn(userEntityList);

        Optional<PlaylistEntity> playlistEntity = Optional.of(playlistEntity1);
        String id = "My Playlist 1";

        when(playlistJpaRepository.findById(id)).thenReturn(playlistEntity);

        List<Playlist> playlists = playlistRepository.findByUserName(userName);

        assertNotNull(playlists);
        assertEquals(1, playlists.size());
        assertEquals(id, playlists.get(0).getId());
    }

    @Test
    public void shouldReturnEmptyListWhenParameterNotInformed() throws Exception {
        when(userJpaRepository.findByNameIgnoreCase(null)).thenReturn(new ArrayList<>());
        when(playlistJpaRepository.findById(null)).thenReturn(Optional.empty());

        List<Playlist> playlists = playlistRepository.findByUserName(null);

        assertTrue(playlists.isEmpty());
    }

}
