package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Music;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicServicePort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl implements MusicServicePort {

    private static final Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class.getName());

    @Autowired
    private MusicRepositoryPort musicRepositoryPort;

    @Override
    public List<MusicDTO> findByNameArtistOrNameMusic(String name) {
        if (StringUtils.isBlank(name)) {
            logger.info("Filter not informed, returning all songs.");
            return findAll();
        }

        if (name.length() < 2) {
            logger.warn("Filter informed with less than 2 characters, throwing exception.");
            throw new BusinessRuleException("Filter must be at least 2 characters long.");
        }

        List<Music> music = musicRepositoryPort.findByNameArtistOrNameMusic(name);

        if (music.isEmpty()) {
            logger.info("Music not found with the filter " + name);
            throw new NotFoundException();
        } else {
            logger.info("Were found " + music.size() + " musics with the filter " + name);
        }

        return music.stream().map(Music::toMusicDTO).collect(Collectors.toList());
    }

    @Override
    public List<MusicDTO> findAll() {
        List<Music> music = musicRepositoryPort.findAll();
        return music.stream().map(Music::toMusicDTO).collect(Collectors.toList());
    }

    @Override
    public MusicDTO findById(String id) {
        if (StringUtils.isBlank(id)) {
            logger.info("Music Id not informed.");
            throw new BusinessRuleException("Music Id not informed.");
        }

        Music music = musicRepositoryPort.findById(id);

        if (music == null) {
            logger.info("Music does not exist.");
            throw new BusinessRuleException("Music does not exist in the database.");
        }

        return music.toMusicDTO();
    }

}
