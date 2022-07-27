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
@Table(name = "Musicas")

public class MusicaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY);
    @Column(name = "Id");
    private String Id;

    @Column(name = "Nome")
    private String Nome;

    @ManyToOne
    @JoinColumn(name = "ArtistaId", referencedColumnName = "Id")
    private ArtistaEntity artistaEntity;

}
