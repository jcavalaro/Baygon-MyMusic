package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities;

import com.ciandt.summit.bootcamp2022.domain.models.Artista;
import com.ciandt.summit.bootcamp2022.domain.models.Musica;
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
public class MusicaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "ArtistaId", referencedColumnName = "Id")
    private ArtistaEntity artista;

    public MusicaEntity(Musica musica) {
        setId(musica.getId());
        setNome(musica.getNome());
        setArtista(new ArtistaEntity(musica.getArtista()));
    }

    public Musica toMusica() {
        return new Musica(getId(), getNome(), new Artista(artista.getId(), artista.getNome()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MusicaEntity that = (MusicaEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
