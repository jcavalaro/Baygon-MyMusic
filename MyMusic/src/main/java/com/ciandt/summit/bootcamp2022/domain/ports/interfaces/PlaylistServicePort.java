package com.ciandt.summit.bootcamp2022.domain.ports.interfaces;

import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;

import java.util.List;

public interface PlaylistServicePort {

    List<PlaylistDTO> findAll();

    PlaylistDTO findById(String id);

    PlaylistDTO addMusicsToPlaylist(String playlistId, DataDTO musics);

    PlaylistDTO removeMusicFromPlaylist(String playlistId, String musicId);

    List<PlaylistDTO> findByUserName(String userName);

}
