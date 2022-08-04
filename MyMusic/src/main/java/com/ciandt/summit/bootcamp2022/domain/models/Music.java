package com.ciandt.summit.bootcamp2022.domain.models;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.ArtistEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Music {

    private String id;
    private String name;
    private Artist artist;

    public Music(MusicDTO musicDTO) {
        setId(musicDTO.getId());
        setName(musicDTO.getName());
        setArtist(new Artist(musicDTO.getArtist()));
    }

    public Music(String id, String name, ArtistDTO artist) {
        setId(id);
        setName(name);
        setArtist(new Artist(artist));
    }

    public MusicDTO toMusicDTO() {
        return new MusicDTO(getId(), getName(), new ArtistDTO(getArtist()));
    }

    public MusicEntity toMusicEntity() {
        return new MusicEntity(getId(), getName(), new ArtistEntity(getArtist()));
    }

}