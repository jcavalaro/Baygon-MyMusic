package com.ciandt.summit.bootcamp2022.domain.ports.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Music;

import java.util.List;

public interface MusicRepositoryPort {

    List<Music> findByNameArtistOrNameMusic(String name);

    List<Music> findAll();

    Music findById(String id);

}
