package com.example.leontismongo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Avaliacao {
    @Schema(description = "ID único da obra",example = "12345")
    private Long obraId;
    @Schema(description = "Nota da obra",example = "3.5")
    private double nota;
    @Schema(description = "Data que a avaliação foi feita",example = "2024-09-25T18:30:00")
    private LocalDateTime dataAvaliacao;

}