package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.dtos.MusicDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.MusicServicePort;
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

@RestController
@RequestMapping("/api/v1/musicas")
@Tag(description = "Music API", name = "Music Controller")
public class MusicController {

    private static final Logger logger = LoggerFactory.getLogger(MusicController.class.getName());
    private static final String SEARCHING = "Searching for musics.";
    private static final String RETURNING = "Returning all musics found.";

    @Autowired
    private MusicServicePort musicServicePort;

    @GetMapping
    @Operation(summary = "Search musics by artist name or song name", description = "Search by artist name or song name. If you do not enter a filter, all songs will be returned. The filter search is case insensitive. The filter must have at least 2 characters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "204", description = "No results"),
            @ApiResponse(responseCode = "400", description = "Not enough characters"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> findMusics(@RequestParam(required = false, name = "filter") String filter) {
        logger.info(SEARCHING);
        List<MusicDTO> musicas = musicServicePort.findByNameArtistOrNameMusic(filter);

        logger.info(RETURNING);
        return new ResponseEntity<>(new DataDTO(musicas), HttpStatus.OK);
    }

}