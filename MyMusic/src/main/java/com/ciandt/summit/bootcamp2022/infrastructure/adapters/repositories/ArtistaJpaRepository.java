package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaJpaRepository extends JpaRepository<Artista, String> {
}
