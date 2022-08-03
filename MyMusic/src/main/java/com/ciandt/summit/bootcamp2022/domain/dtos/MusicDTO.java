package com.ciandt.summit.bootcamp2022.domain.dtos;

import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.ArtistEntity;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MusicDTO {

    @NotBlank(message = "Music must contain id")
    private String id;

    @NotBlank(message = "Music must contain name")
    private String name;

    @Valid
    @NotNull(message = "Music must contain artist")
    private ArtistDTO artist;

    public MusicEntity toMusicEntity() {
        return new MusicEntity(getId(), getName(), new ArtistEntity(getArtist()));
    }

}
