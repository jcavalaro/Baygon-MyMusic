package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities;

import com.ciandt.summit.bootcamp2022.domain.models.Playlist;
import com.ciandt.summit.bootcamp2022.domain.models.Usuario;
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
@Table(name = "Usuarios")
public class UsuarioEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;

    @Column(name = "Nome")
    private String nome;

    @OneToOne
    @JoinColumn(name = "PlaylistId", referencedColumnName = "Id")
    private PlaylistEntity playlist;

    public UsuarioEntity(Usuario usuario) {
        setId(usuario.getId());
        setNome(usuario.getNome());
        setPlaylist(new PlaylistEntity(usuario.getPlaylist()));
    }

    public Usuario toUsuario() {
        return new Usuario(getId(), getNome(), new Playlist(playlist.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
