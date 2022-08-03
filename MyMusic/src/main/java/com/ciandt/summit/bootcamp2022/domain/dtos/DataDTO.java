package com.ciandt.summit.bootcamp2022.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO {

    @Valid
    @NotEmpty(message = "The data cannot be empty")
    private List<MusicDTO> data;

}
