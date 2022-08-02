package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;
import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domain.models.Musica;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities.MusicaEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/playlists")
@Tag(description = "Api para gerenciar playlists com musicas", name = "Playlist Controller")
public class PlaylistController {

    @Autowired
    private PlaylistServicePort playlistServicePort;

    @PostMapping("/{id}/musics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Musica(s) adicionada(s) na playlist "),
            @ApiResponse(responseCode = "400", description = "Musica não existe na base de dados"),
            @ApiResponse(responseCode = "400", description = "Playlist não existe na base de dados")})
    public ResponseEntity<PlaylistDTO> addMusic(@PathVariable("id") Long id, @RequestBody DataDTO dataDTO){

        List<Musica> musicaList = dataDTO.getData().stream().map(MusicaDTO::toMusica).collect(Collectors.toList());
       // List<Musica> musicaList2 = findByid();

        for (Musica m: musicaList){
            boolean bol= musicaList2.stream().anyMatch(mus -> mus.getId().equals(m.getId()));
            if (bol){
                break;
            }else {
                musicaList.add(m);
            }
        }



        boolean valid = musicaList.stream().findAny().equals(musica1);

        System.out.println(valid);
        return ResponseEntity.ok(new PlaylistDTO());
    }
}


