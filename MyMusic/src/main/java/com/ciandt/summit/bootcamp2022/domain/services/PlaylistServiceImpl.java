package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Music;
import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.PlaylistRepositoryPort;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.BusinessRuleException;
import com.ciandt.summit.bootcamp2022.domain.services.exceptions.NotFoundException;
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
    private static final String PLAYLIST_NOT_INFORMED = "Playlist Id not informed.";
    private static final String MUSIC_NOT_INFORMED = "Music Id not informed.";
    private static final String PLAYLIST_DOES_NOT_EXISTS = "Playlist does not exist in the database.";
    private static final String MUSIC_DOES_NOT_EXISTS = "Music does not exist in the database.";
    private static final String MUSIC_NOT_EXISTS_IN_PLAYLIST = "Music does not exist in the playlist.";
    private static final String MUSIC_DOES_NOT_EXISTS_IN_PLAYLIST = "Music %s does not exist in the playlist %s.";
    private static final String MUSIC_EXISTS_IN_PLAYLIST = "Music %s already exists in the playlist %s.";
    private static final String PLAYLIST_NOT_FOUND = "Playlists not found with the user name %s.";
    private static final String PLAYLIST_FOUND = "Were found %d playlists with the user name %s.";

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
            logger.info(PLAYLIST_NOT_INFORMED);
            throw new BusinessRuleException(PLAYLIST_NOT_INFORMED);
        }

        Playlist playlist = playlistRepositoryPort.findById(id);

        if (playlist == null) {
            logger.info(PLAYLIST_DOES_NOT_EXISTS);
            throw new BusinessRuleException(PLAYLIST_DOES_NOT_EXISTS);
        }

        return playlist.toPlaylistDTO();
    }

    @Override
    public PlaylistDTO addMusicsToPlaylist(String playlistId, DataDTO musics) {
        if (StringUtils.isBlank(playlistId)) {
            logger.info(PLAYLIST_NOT_INFORMED);
            throw new BusinessRuleException(PLAYLIST_NOT_INFORMED);
        }

        Playlist playlist = playlistRepositoryPort.findById(playlistId);
        if (playlist == null) {
            logger.info(PLAYLIST_DOES_NOT_EXISTS);
            throw new BusinessRuleException(PLAYLIST_DOES_NOT_EXISTS);
        }

        PlaylistEntity playlistEntity = playlist.toPlaylistEntity();

        List<MusicEntity> musicEntityList = musics.getData().stream().map(MusicDTO::toMusicEntity).collect(Collectors.toList());
        for (MusicEntity music : musicEntityList) {
             if (musicRepositoryPort.findById(music.getId()) == null) {
                 logger.info(MUSIC_DOES_NOT_EXISTS);
                 throw new BusinessRuleException(MUSIC_DOES_NOT_EXISTS);
            }

             if (playlistEntity.getMusics().stream().noneMatch(m -> music.getId().equals(m.getId()))) {
                 logger.info(String.format(MUSIC_DOES_NOT_EXISTS_IN_PLAYLIST, music.getId(), playlistEntity.getId()));
                 playlistEntity.getMusics().add(music);
             } else {
                 logger.info(String.format(MUSIC_EXISTS_IN_PLAYLIST, music.getId(), playlistEntity.getId()));
             }
        }

        return playlistRepositoryPort.addMusicsToPlaylist(playlistEntity).toPlaylistDTO();
    }

    @Override
    public PlaylistDTO removeMusicFromPlaylist(String playlistId, String musicId) {
        if (StringUtils.isBlank(playlistId)) {
            logger.info(PLAYLIST_NOT_INFORMED);
            throw new BusinessRuleException(PLAYLIST_NOT_INFORMED);
        }

        if (StringUtils.isBlank(musicId)) {
            logger.info(MUSIC_NOT_INFORMED);
            throw new BusinessRuleException(MUSIC_NOT_INFORMED);
        }

        Playlist playlist = playlistRepositoryPort.findById(playlistId);
        if (playlist == null) {
            logger.info(PLAYLIST_DOES_NOT_EXISTS);
            throw new BusinessRuleException(PLAYLIST_DOES_NOT_EXISTS);
        }

        PlaylistEntity playlistEntity = playlist.toPlaylistEntity();

        Music music = musicRepositoryPort.findById(musicId);
        if (music == null) {
            logger.info(MUSIC_DOES_NOT_EXISTS);
            throw new BusinessRuleException(MUSIC_DOES_NOT_EXISTS);
        }

        MusicEntity musicEntity = music.toMusicEntity();

        if (playlistEntity.getMusics().stream().noneMatch(m -> m.equals(musicEntity))) {
            logger.info(String.format(MUSIC_DOES_NOT_EXISTS_IN_PLAYLIST, music.getId(), playlistEntity.getId()));
            throw new BusinessRuleException(MUSIC_NOT_EXISTS_IN_PLAYLIST);
        } else {
            playlistEntity.getMusics().remove(musicEntity);
            logger.info(String.format(MUSIC_EXISTS_IN_PLAYLIST, music.getId(), playlistEntity.getId()));
        }

        return playlistRepositoryPort.removeMusicFromPlaylist(playlistEntity).toPlaylistDTO();
    }

    @Override
    public List<PlaylistDTO> findByUserName(String userName) {
        List<Playlist> playlists = playlistRepositoryPort.findByUserName(userName);

        if (playlists.isEmpty()) {
            logger.info(String.format(PLAYLIST_NOT_FOUND, userName));
            throw new NotFoundException();
        } else {
            logger.info(String.format(PLAYLIST_FOUND, playlists.size(), userName));
        }

        return playlists.stream().map(Playlist::toPlaylistDTO).collect(Collectors.toList());
    }

}
