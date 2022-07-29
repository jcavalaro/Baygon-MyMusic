package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicaServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/musicas")
@Tag(description = "Api Para Gerenciar Musicas", name = "Musica Controller")
public class MusicaController {

    @Autowired
    private  MusicaServicePort musicaServicePort;

    @GetMapping
    @Operation(summary = "Buscar musicas por nome artista ou nome musica", description = "Buscar por nome artista ou nome musica. Caso não seja informado o filtro, todas as musicas serão retornadas. A busca com filtro é case insensitive. Para o filtro é necessário ter ao menos 2 caracteres.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
            @ApiResponse(responseCode = "204", description = "Sem resultados"),
            @ApiResponse(responseCode = "400", description = "Caracteres insuficientes")})
    public ResponseEntity<DataDTO> findMusicas(@RequestParam(required = false, name = "filtro") String filtro) {
        List<MusicaDTO> musicas = musicaServicePort.findByNameArtistaOrNameMusica(filtro);

        return new ResponseEntity<>(new DataDTO(musicas), HttpStatus.OK);
    }

}