package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.PlaylistRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.PlaylistEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistServicePort {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistServiceImpl.class.getName());

    @Autowired
    private PlaylistRepositoryPort playlistRepositoryPort;

    @Autowired
    private MusicRepositoryPort musicRepositoryPort;

    @Override
    public List<PlaylistDTO> findAll() {
        List<Playlist> playlists = playlistRepositoryPort.findAll();
        return playlists.stream().map(Playlist::toPlaylistDTO).collect(Collectors.toList());
    }

    @Override
    public PlaylistDTO findById(String id) {
        if (StringUtils.isBlank(id)) {
            logger.info("Playlist Id not informed.");
            throw new BusinessRuleException("Playlist Id not informed.");
        }

        Playlist playlist = playlistRepositoryPort.findById(id);

        if (playlist == null) {
            logger.info("Playlist does not exist.");
            throw new BusinessRuleException("Playlist does not exist in the database.");
        }

        return playlist.toPlaylistDTO();
    }

    @Override
    public PlaylistDTO addMusicsToPlaylist(String playlistId, DataDTO musics) {
        if (StringUtils.isBlank(playlistId)) {
            logger.info("Playlist Id not informed.");
            throw new BusinessRuleException("Playlist Id not informed.");
        }

        Playlist playlist = playlistRepositoryPort.findById(playlistId);
        if (playlist == null) {
            logger.info("Playlist does not exist.");
            throw new BusinessRuleException("Playlist does not exist in the database.");
        }

        PlaylistEntity playlistEntity = playlist.toPlaylistEntity();

        List<MusicEntity> musicEntityList = musics.getData().stream().map(MusicDTO::toMusicEntity).collect(Collectors.toList());
        for (MusicEntity music : musicEntityList) {
             if (musicRepositoryPort.findById(music.getId()) == null) {
                 logger.info("Music does not exist.");
                 throw new BusinessRuleException("Music does not exist in the database.");
            }

             if (!playlist.getMusics().stream().anyMatch(m -> music.getId().equals(m.getId()))) {
                 logger.info("Music " + music.getId() + " does not exist in the playlist " + playlistId + ", adding.");
                 playlistEntity.getMusics().add(music);
             } else {
                 logger.info("Music " + music.getId() + " already exists in the playlist " + playlistId);
             }
        }

        return playlistRepositoryPort.addMusicsToPlaylist(playlistEntity).toPlaylistDTO();
    }

}
