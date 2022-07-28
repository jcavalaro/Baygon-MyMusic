package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MusicaJpaRepository extends JpaRepository<MusicaEntity, String> {
    List<MusicaEntity> findByNomeIgnoreCase(String filtro);
}
