package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.MusicaDTO;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicaServicePort;
import com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers.exceptions.RuleLengthViolationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/musicas")
@Tag(description = "Api Para Gerenciar Musicas", name = "Musica Controller")
public class MusicaController {

    private static final Logger logger = LoggerFactory.getLogger(MusicaController.class.getName());

    @Autowired
    private  MusicaServicePort musicaServicePort;

    @GetMapping
    @Operation(summary = "Buscar musicas por nome artista ou nome musica", description = "Buscar por nome artista ou nome musica. Caso não seja informado o filtro, todas as musicas serão retornadas. A busca com filtro é case insensitive. Para o filtro é necessário ter ao menos 2 caracteres.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
            @ApiResponse(responseCode = "204", description = "Sem resultados"),
            @ApiResponse(responseCode = "400", description = "Caracteres insuficientes")})
    public ResponseEntity<?> findMusicas(@RequestParam(required = false, name = "filtro") Optional<String> filtro) {
        if (!filtro.isPresent()) {
            logger.info("Filtro não informado, retornando todas as musicas.");
            return new ResponseEntity<>(new DataDTO(musicaServicePort.findAll()), HttpStatus.OK);
        }

        if (filtro.get().length() < 2) {
            logger.warn("Filtro informado com menos de 2 caracteres, lançando exceção.");
            throw new RuleLengthViolationException();
        }

        List<MusicaDTO> musicas = musicaServicePort.findByNameArtistaOrNameMusica(filtro.get());

        if (musicas.isEmpty()) {
            logger.info("Musicas não encontradas com o filtro " + filtro.get());
            return new ResponseEntity<>(new DataDTO(), HttpStatus.NO_CONTENT);
        }

        logger.info("Foram encontradas " + musicas.size() + " musicas com o filtro " + filtro.get());
        return new ResponseEntity<>(new DataDTO(musicas), HttpStatus.OK);
    }

}