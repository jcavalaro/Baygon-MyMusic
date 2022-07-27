package com.ciandt.summit.bootcamp2022.domain.ports.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Musica;

import java.util.List;

public interface MusicaRepositoryPort {

    List<Musica> findMusicasByFiltro(String filtro);

    List<Musica> findAll();

}
