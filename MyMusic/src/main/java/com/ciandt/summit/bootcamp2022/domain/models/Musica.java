package com.ciandt.summit.bootcamp2022.domain.models;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistaDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
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
    private Artista artista;

    public Musica(MusicaDTO musicaDTO) {
        setId(musicaDTO.getId());
        setNome(musicaDTO.getNome());
        setArtista(new Artista(musicaDTO.getArtista()));
    }

    public Musica(String id, String nome, ArtistaDTO artista) {
        setId(id);
        setNome(nome);
        setArtista(new Artista(artista));
    }

    public MusicaDTO toMusicaDTO() {
        return new MusicaDTO(getId(), getNome(), new ArtistaDTO(getArtista()));
    }

}