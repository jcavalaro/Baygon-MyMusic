package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.PlaylistRepositoryPort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.PlaylistEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlaylistRepository implements PlaylistRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistRepository.class.getName());

    @Autowired
    private PlaylistJpaRepository playlistJpaRepository;

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
    public void addMusicsToPlaylist(PlaylistEntity playlist) {
        playlistJpaRepository.save(playlist);
        logger.info("Musics added successfully!");
    }

}
