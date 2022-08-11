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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl implements MusicServicePort {

    private static final Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class.getName());
    private static final String FILTER_NOT_INFORMED = "Filter not informed, returning all songs.";
    private static final String FILTER_INFORMED_EXCEPTION = "Filter must be at least 2 characters long.";
    private static final String MUSIC_NOT_INFORMED = "Music Id not informed.";
    private static final String MUSIC_DOES_NOT_EXISTS = "Music does not exist in the database.";
    private static final String MUSIC_NOT_FOUND = "Music not found with the filter %s.";
    private static final String MUSICS_FOUND = "Were found %d musics with the filter %s.";

    @Autowired
    private MusicRepositoryPort musicRepositoryPort;

    @Override
    @Cacheable(value = "musics_cache")
    public List<MusicDTO> findByNameArtistOrNameMusic(String name) {
        if (StringUtils.isBlank(name)) {
            logger.info(FILTER_NOT_INFORMED);
            return findAll();
        }

        if (name.length() < 2) {
            logger.warn(FILTER_INFORMED_EXCEPTION);
            throw new BusinessRuleException(FILTER_INFORMED_EXCEPTION);
        }

        List<Music> music = musicRepositoryPort.findByNameArtistOrNameMusic(name);

        if (music.isEmpty()) {
            logger.info(String.format(MUSIC_NOT_FOUND, name));
            throw new NotFoundException();
        } else {
            logger.info(String.format(MUSICS_FOUND, music.size(), name));
        }

        return music.stream().map(Music::toMusicDTO).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "musics_cache")
    public List<MusicDTO> findAll() {
        List<Music> music = musicRepositoryPort.findAll();
        return music.stream().map(Music::toMusicDTO).collect(Collectors.toList());
    }

    @Override
    public MusicDTO findById(String id) {
        if (StringUtils.isBlank(id)) {
            logger.info(MUSIC_NOT_INFORMED);
            throw new BusinessRuleException(MUSIC_NOT_INFORMED);
        }

        Music music = musicRepositoryPort.findById(id);

        if (music == null) {
            logger.info(MUSIC_DOES_NOT_EXISTS);
            throw new BusinessRuleException(MUSIC_DOES_NOT_EXISTS);
        }

        return music.toMusicDTO();
    }

}
