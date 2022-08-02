package com.ciandt.summit.bootcamp2022.infrastructure.adapters.controllers;

import com.ciandt.summit.bootcamp2022.domain.dtos.DataDTO;
import com.ciandt.summit.bootcamp2022.domain.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domain.ports.interfaces.PlaylistServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/playlists")
@Tag(description = "Playlist API", name = "Playlist Controller")
public class PlaylistController {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class.getName());

    @Autowired
    private PlaylistServicePort playlistServicePort;

    @PostMapping("/{playlistId}/musicas")
    @Operation(summary = "Add songs to a playlist", description = "Receives a list of songs and adds them to the playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Music Does Not Exist in the Base OR Playlist Does Not Exist in the Base or Payload Body Does Not Conform to Documentation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> addMusicsToPlaylist(@PathVariable("playlistId") String playlistId, @Valid @RequestBody DataDTO musics) {
        logger.info("Adding songs to the playlist " + playlistId);
        playlistServicePort.addMusicsToPlaylist(playlistId, musics);

        logger.info("Returning updated playlist");
        PlaylistDTO playlistDTO = playlistServicePort.findById(playlistId);
        return new ResponseEntity<>(playlistDTO, HttpStatus.CREATED);
    }

}
