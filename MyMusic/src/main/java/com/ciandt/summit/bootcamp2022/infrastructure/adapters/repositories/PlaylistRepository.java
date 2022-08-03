package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.PlaylistRepositoryPort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.PlaylistEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaylistRepository implements PlaylistRepositoryPort {

    @Autowired
    private PlaylistJpaRepository playlistJpaRepository;

    @Override
    public Playlist findPlaylistById(String id) {
        Optional<PlaylistEntity> playlist = playlistJpaRepository.findById(id);
        return playlist.get().toPlayList();
    }

    @Override
    public List<Playlist> findAll() {
        List<PlaylistEntity> playlist = playlistJpaRepository.findAll();
        return playlist.stream().map(PlaylistEntity::toPlayList).collect(Collectors.toList());
    }

    @Override
    public void addMusicRepository(PlaylistEntity playlist) {
        playlistJpaRepository.save(playlist);
    }
}
