package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicJpaRepository extends JpaRepository<MusicEntity, String> {

    @Query("SELECT m FROM MusicEntity m JOIN ArtistEntity a on a.id = m.artist WHERE lower(a.name) like lower(concat('%', :name,'%')) or lower(m.name) like lower(concat('%', :name,'%')) ORDER BY a.name, m.name ASC")
    List<MusicEntity> findByNameArtistOrNameMusic(@Param("name") String name);

}
