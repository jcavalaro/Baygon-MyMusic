package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicaJpaRepository extends JpaRepository<MusicaEntity, String> {

    @Query("SELECT m FROM MusicaEntity m JOIN ArtistaEntity a on a.id = m.artista WHERE lower(a.nome) like lower(concat('%', :name,'%')) or lower(m.nome) like lower(concat('%', :name,'%')) ORDER BY a.nome, m.nome ASC")
    List<MusicaEntity> findByNameArtistaOrNameMusica(@Param("name") String name);

}
