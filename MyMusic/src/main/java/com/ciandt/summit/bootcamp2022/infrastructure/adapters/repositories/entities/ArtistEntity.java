package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities;

import com.ciandt.summit.bootcamp2022.domain.dtos.ArtistDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Artistas")
public class ArtistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String name;

    public ArtistEntity(Artist artist) {
        setId(artist.getId());
        setName(artist.getName());
    }

    public ArtistEntity(ArtistDTO artistDTO) {
        setId(artistDTO.getId());
        setName(artistDTO.getName());
    }

    public Artist toArtista() {
        return new Artist(getId(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ArtistEntity that = (ArtistEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
