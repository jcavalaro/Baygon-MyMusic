package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicaServicePort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicaRepositoryPort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.MusicaController;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions.RuleLengthViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicaServiceImpl implements MusicaServicePort {

    private static final Logger logger = LoggerFactory.getLogger(MusicaController.class.getName());

    @Autowired
    private MusicaRepositoryPort musicaRepositoryPort;

    @Override
    public List<MusicaDTO> findByNameArtistaOrNameMusica(String name) {
        if (name.isEmpty()) {
            logger.info("Filtro não informado, retornando todas as musicas.");
            return findAll();
        }

        if (name.length() < 2) {
            logger.warn("Filtro informado com menos de 2 caracteres, lançando exceção.");
            throw new RuleLengthViolationException();
        }

        List<Musica> musicas = musicaRepositoryPort.findByNameArtistaOrNameMusica(name);

        if (musicas.isEmpty()) {
            logger.info("Musicas não encontradas com o filtro " + name);
        } else {
            logger.info("Foram encontradas " + musicas.size() + " musicas com o filtro " + name);
        }


        return musicas.stream().map(Musica::toMusicaDTO).collect(Collectors.toList());
    }

    @Override
    public List<MusicaDTO> findAll() {
        List<Musica> musicas = musicaRepositoryPort.findAll();
        return musicas.stream().map(Musica::toMusicaDTO).collect(Collectors.toList());
    }


}
