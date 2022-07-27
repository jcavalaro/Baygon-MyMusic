package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "Playlists")
public class PlaylistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;

    @ManyToMany
    @JoinTable(name = "PlaylistMusicas",
            joinColumns = @JoinColumn(name = "PlaylistId", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "MusicaId", referencedColumnName = "Id"))
    private List<MusicaEntity> musicas = new ArrayList<>();


}
