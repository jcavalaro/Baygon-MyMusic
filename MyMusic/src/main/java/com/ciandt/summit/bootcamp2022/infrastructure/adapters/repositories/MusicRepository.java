package com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domain.models.Music;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.MusicRepositoryPort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MusicRepository implements MusicRepositoryPort {

    @Autowired
    private MusicJpaRepository musicJpaRepository;

    @Override
    public List<Music> findByNameArtistOrNameMusic(String name) {
        List<MusicEntity> musics = musicJpaRepository.findByNameArtistOrNameMusic(name);
        return musics.stream().map(MusicEntity::toMusic).collect(Collectors.toList());
    }

    @Override
    public List<Music> findAll() {
        List<MusicEntity> musics = musicJpaRepository.findAll();
        return musics.stream().map(MusicEntity::toMusic).collect(Collectors.toList());
    }

    @Override
    public Music findById(String id) {
        Optional<MusicEntity> musicEntity = musicJpaRepository.findById(id);
        return musicEntity.isEmpty() ? null : musicEntity.get().toMusic();
    }

}
