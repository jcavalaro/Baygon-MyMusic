package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities;

import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Playlists")
public class PlaylistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;

    @Column(name = "musicaId")
    private String musicId;

    @ManyToMany
    @JoinTable(name = "PlaylistMusicas",
            joinColumns = @JoinColumn(name = "PlaylistId", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "musicId", referencedColumnName = "Id"))
    private List<MusicaEntity> musicas = new ArrayList<>();

    public PlaylistEntity(Playlist playlist) {
        setId(playlist.getId());
    }

    public Playlist toPlayList() {
        return new Playlist(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlaylistEntity that = (PlaylistEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
