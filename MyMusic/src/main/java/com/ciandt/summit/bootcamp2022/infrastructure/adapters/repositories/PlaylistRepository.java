package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.PlaylistRepositoryPort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.PlaylistEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlaylistRepository implements PlaylistRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistRepository.class.getName());
    private static final String MUSICS_ADDED = "Musics added successfully!";
    private static final String MUSIC_REMOVE = "Music remove from playlist successfully.";

    @Autowired
    private PlaylistJpaRepository playlistJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public List<Playlist> findAll() {
        List<PlaylistEntity> playlist = playlistJpaRepository.findAll();
        return playlist.stream().map(PlaylistEntity::toPlaylist).collect(Collectors.toList());
    }

    @Override
    public Playlist findById(String id) {
        Optional<PlaylistEntity> playlistEntity = playlistJpaRepository.findById(id);
        return playlistEntity.isEmpty() ? null : playlistEntity.get().toPlaylist();
    }

    @Override
    public Playlist addMusicsToPlaylist(PlaylistEntity playlist) {
        logger.info(MUSICS_ADDED);
        return playlistJpaRepository.save(playlist).toPlaylist();
    }

    @Override
    public Playlist removeMusicFromPlaylist(PlaylistEntity playlist) {
        logger.info(MUSIC_REMOVE);
        return playlistJpaRepository.save(playlist).toPlaylist();
    }

    @Override
    public List<Playlist> findByUserName(String userName) {
        List<UserEntity> userEntity = userJpaRepository.findByNameIgnoreCase(userName);

        List<PlaylistEntity> playlist = new ArrayList<>();
        for (UserEntity user : userEntity) {
            Optional<PlaylistEntity> playlistEntity = playlistJpaRepository.findById(user.getPlaylist().getId());
            playlist.add(playlistEntity.get());
        }

        return playlist.stream().map(PlaylistEntity::toPlaylist).collect(Collectors.toList());
    }

}
