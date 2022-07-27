package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "Playlists")
public class PlaylistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
