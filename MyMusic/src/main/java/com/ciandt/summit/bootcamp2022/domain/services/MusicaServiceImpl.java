package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicaServicePort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicaRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicaServiceImpl implements MusicaServicePort {

    @Autowired
    private MusicaRepositoryPort musicaRepositoryPort;

    @Override
    public List<MusicaDTO> findByNameArtistaOrNameMusica(String name) {
        List<Musica> musicas = musicaRepositoryPort.findByNameArtistaOrNameMusica(name);
        return musicas.stream().map(Musica::toMusicaDTO).collect(Collectors.toList());
    }

    @Override
    public List<MusicaDTO> findAll() {
        List<Musica> musicas = musicaRepositoryPort.findAll();
        return musicas.stream().map(Musica::toMusicaDTO).collect(Collectors.toList());
    }


}
