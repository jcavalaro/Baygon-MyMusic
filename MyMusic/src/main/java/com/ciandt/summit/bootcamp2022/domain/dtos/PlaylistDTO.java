package com.ciandt.summit.bootcamp2022.domain.dtos;

import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTO {

    private String id;

    private List<Musica> musicas = new ArrayList<>();
    
    public PlaylistDTO(Playlist playlist) {
    
        playlist.setId(id);
        playlist.setMusicas(playlist.getMusicas());

    }
}
