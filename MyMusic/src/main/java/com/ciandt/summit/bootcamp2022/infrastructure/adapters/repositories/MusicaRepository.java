package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicaRepositoryPort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicaRepository implements MusicaRepositoryPort {

    @Autowired
    private MusicaJpaRepository musicaJpaRepository;

    public List<Musica> findAll() {
        List<MusicaEntity> musicaEntities = this.musicaJpaRepository.findAll();
        return musicaEntities.stream().map(MusicaEntity::toMusica).collect(Collectors.toList());
    }

    public List<Musica> findByNameArtistaOrNameMusica(String filtro) {
        List<MusicaEntity> musicaEntities = this.musicaJpaRepository.findByNameArtistaOrNameMusica(filtro);
        return musicaEntities.stream().map(MusicaEntity::toMusica).collect(Collectors.toList());
    }
}
