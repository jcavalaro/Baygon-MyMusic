package com.ciandt.summit.bootcamp2022.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlaylistDTO {

    private String id;
    private List<MusicDTO> musics;

}
