package com.ciandt.summit.bootcamp2022.domain.models;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Artista {

    private String id;
    private String nome;

    public Artista(ArtistaDTO artista) {
        setId(artista.getId());
        setNome(artista.getNome());
    }

}
