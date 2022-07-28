package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicaRepositoryPort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicaRepository implements MusicaRepositoryPort {

    @Autowired
    private MusicaJpaRepository musicaJpaRepository;

    @Override
    public List<Musica> findByNameArtistaOrNameMusica(String name) {
        List<MusicaEntity> musicas = musicaJpaRepository.findByNameArtistaOrNameMusica(name);
        return musicas.stream().map(MusicaEntity::toMusica).collect(Collectors.toList());
    }

    @Override
    public List<Musica> findAll() {
        List<MusicaEntity> musicas = musicaJpaRepository.findAll();
        return musicas.stream().map(MusicaEntity::toMusica).collect(Collectors.toList());
    }

}
