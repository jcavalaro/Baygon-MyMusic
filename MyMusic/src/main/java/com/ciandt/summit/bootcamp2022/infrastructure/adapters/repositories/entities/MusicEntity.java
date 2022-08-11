package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities;

import com.ciandt.summit.bootcamp2022.domain.models.Artist;
import com.ciandt.summit.bootcamp2022.domain.models.Music;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Musicas")
public class MusicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ArtistaId", referencedColumnName = "Id")
    private ArtistEntity artist;

    public MusicEntity(Music music) {
        setId(music.getId());
        setName(music.getName());
        setArtist(new ArtistEntity(music.getArtist()));
    }

    public Music toMusic() {
        return new Music(getId(), getName(), new Artist(artist.getId(), artist.getName()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MusicEntity that = (MusicEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
