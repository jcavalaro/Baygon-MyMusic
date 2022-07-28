package com.ciandt.summit.bootcamp2022.domain.dtos;

import com.ciandt.summit.bootcamp2022.domain.models.Artista;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistaDTO {

    private String id;
    private String nome;

    public ArtistaDTO(Artista artista) {
        setId(artista.getId());
        setNome(artista.getNome());
    }

    public Artista toArtista() {
        return new Artista(getId(), getNome());
    }

}
