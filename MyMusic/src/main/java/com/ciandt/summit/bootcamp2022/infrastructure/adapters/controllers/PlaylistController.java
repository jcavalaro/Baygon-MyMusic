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
import java.util.List;

@RestController
@RequestMapping("/api/v1/playlists")
@Tag(description = "Playlist API", name = "Playlist Controller")
public class PlaylistController {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class.getName());
    private static final String ADDING_MUSICS = "Adding musics to the playlist %s.";
    private static final String REMOVING_MUSIC = "Removing music %s from the playlist %s.";
    private static final String UPDATED_PLAYLIST = "Returning updated playlist.";
    private static final String SEARCHING = "Searching for playlist.";
    private static final String RETURNING = "Returning playlist found.";

    @Autowired
    private PlaylistServicePort playlistServicePort;

    @PostMapping("/{playlistId}/musicas")
    @Operation(summary = "Add musics to a playlist", description = "Receives a list of musics and adds them to the playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Music Does Not Exist in the Base OR Playlist Does Not Exist in the Base or Payload Body Does Not Conform to Documentation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> addMusicsToPlaylist(@PathVariable("playlistId") String playlistId, @Valid @RequestBody DataDTO musics) {
        logger.info(String.format(ADDING_MUSICS, playlistId));
        PlaylistDTO playlistDTO = playlistServicePort.addMusicsToPlaylist(playlistId, musics);

        logger.info(UPDATED_PLAYLIST);
        return new ResponseEntity<>(playlistDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{playlistId}/musicas/{musicaId}")
    @Operation(summary = "Remove music from playlist", description = "Receives music and removes it from the playlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Music Does Not Exist in the Base OR Playlist Does Not Exist in the Base"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> removeMusicFromPlaylist(@PathVariable("playlistId") String playlistId, @PathVariable("musicaId") String musicId) {
        logger.info(String.format(REMOVING_MUSIC, musicId, playlistId));
        PlaylistDTO playlistDTO = playlistServicePort.removeMusicFromPlaylist(playlistId, musicId);

        logger.info(UPDATED_PLAYLIST);
        return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Search playlist by user name", description = "Search by user name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "204", description = "No results"),
            @ApiResponse(responseCode = "400", description = "Parameter Not Informed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> findPlaylist(@RequestParam(name = "user") String userName) {
        logger.info(SEARCHING);
        List<PlaylistDTO> playlistDTO = playlistServicePort.findByUserName(userName);

        logger.info(RETURNING);
        return new ResponseEntity<>(playlistDTO, HttpStatus.OK);
    }

}
