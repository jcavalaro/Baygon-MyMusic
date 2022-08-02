package com.ciandt.summit.bootcamp2022.domain.models;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.PlaylistEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Playlist {

    private String id;
    private List<Music> musics;

    public PlaylistEntity toPlaylistEntity() {
        return new PlaylistEntity(getId());
    }

    public PlaylistDTO toPlaylistDTO() {
        return new PlaylistDTO(getId(), getMusics().stream().map(Music::toMusicDTO).collect(Collectors.toList()));
    }

}
