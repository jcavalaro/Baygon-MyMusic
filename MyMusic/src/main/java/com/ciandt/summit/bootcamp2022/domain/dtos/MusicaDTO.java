package com.ciandt.summit.bootcamp2022.domain.dtos;

import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MusicaDTO {

    private String id;
    private String nome;
    private ArtistaDTO artista;

    public Musica toMusica() {
        return new Musica(getId(), getNome(), getArtista());
    }

}
