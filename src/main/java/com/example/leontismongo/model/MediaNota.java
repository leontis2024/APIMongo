package com.example.leontismongo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MediaNota {
    @Schema(description = "A média das notas", example = "4.5")
    private Double mediaNota;
}
