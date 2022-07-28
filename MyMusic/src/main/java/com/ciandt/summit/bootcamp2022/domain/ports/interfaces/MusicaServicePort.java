package com.ciandt.summit.bootcamp2022.domain.ports.interfaces;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;

import java.util.List;

public interface MusicaServicePort {

    List<MusicaDTO> findByNameArtistaOrNameMusica(String name);
    List<MusicaDTO> findAll();

}
