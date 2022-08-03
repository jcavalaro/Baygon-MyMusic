package com.ciandt.summit.bootcamp2022.domain.ports.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.PlaylistEntity;

import java.util.List;

public interface PlaylistRepositoryPort {
    List<Playlist> findAll();

    Playlist findById(String id);

    Playlist addMusicsToPlaylist(PlaylistEntity playlist);

}
