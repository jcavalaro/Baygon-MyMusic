package com.ciandt.summit.bootcamp2022.domain.ports.interfaces;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;

import java.util.List;

public interface MusicServicePort {

    List<MusicDTO> findByNameArtistOrNameMusic(String name);

    List<MusicDTO> findAll();

    MusicDTO findById(String id);

}
