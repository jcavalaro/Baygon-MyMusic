package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities;

import com.ciandt.summit.bootcamp2022.domain.models.Artista;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "Artistas")
public class ArtistaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String nome;

    public ArtistaEntity(Artista artista) {
        setId(artista.getId());
        setNome(artista.getNome());
    }

}
