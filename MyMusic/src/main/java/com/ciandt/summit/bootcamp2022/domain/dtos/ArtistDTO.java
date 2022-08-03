package com.ciandt.summit.bootcamp2022.domain.dtos;

import com.ciandt.summit.bootcamp2022.domain.models.Artist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {

    @NotBlank(message = "Artist must contain id")
    private String id;

    @NotBlank(message = "Artist must contain name")
    private String name;

    public ArtistDTO(Artist artist) {
        setId(artist.getId());
        setName(artist.getName());
    }

    public Artist toArtist() {
        return new Artist(getId(), getName());
    }

}
