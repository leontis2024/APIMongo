package com.example.leontismongo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatusGuiaMostrar {
    @Schema(description = "Status do Guia",example = "true")
    private boolean concluido;
    @Schema(description = "Posição em que o guia foi deixado se não estiver completo",example = "4")
    private int numeroPassoAtual;
}
