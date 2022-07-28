package com.ciandt.summit.bootcamp2022.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Musica {

    private String id;
    private String nome;
    private Artista artistaId;

}