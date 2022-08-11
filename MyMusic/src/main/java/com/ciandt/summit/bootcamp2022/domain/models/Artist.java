package com.ciandt.summit.bootcamp2022.domain.models;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Artist {

    private String id;
    private String name;

    public Artist(ArtistDTO artistDTO) {
        setId(artistDTO.getId());
        setName(artistDTO.getName());
    }

}
