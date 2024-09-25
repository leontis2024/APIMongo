package com.example.leontismongo;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class HistoricoObras {
    @Schema(description = "ID único da obra",example = "12345")
    private Long obraId;
    @Schema(description = "Data que a obra foi escaneada",example = "2024-09-25T18:30:00")
    private LocalDateTime dataEscaneada;

}