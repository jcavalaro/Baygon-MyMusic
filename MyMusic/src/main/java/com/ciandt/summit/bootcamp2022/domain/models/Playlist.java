package com.ciandt.summit.bootcamp2022.domain.models;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistaDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Playlist {


    List<Musica> playlistMusic = new ArrayList<>();

    private String id;
    private String musicId;
    private List<Musica> musicas = new ArrayList<>();

    public Playlist(PlaylistDTO playlistDTO) {
        playlistDTO.setId(id);
        playlistDTO.setMusicId(musicId);
        playlistDTO.setMusicas(playlistDTO.getMusicas());
    }
}

