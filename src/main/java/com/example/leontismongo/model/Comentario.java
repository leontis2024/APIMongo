package com.example.leontismongo.model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class Comentario {

    @Schema(description = "ID único da obra",example = "12345")
    private Long obraId;
    @Schema(description = "Comentário do usuário",example = "Gostei muito da localização dessa obra, ela também é muito bonita e expressa muito bem o que o artista estava sentindo nos fazendo refletir sobre o que ele e")
    private String texto;
    @Schema(description = "Data que o comentário foi feito",example = "2024-09-25T18:30:00")
    private LocalDateTime dataComentario;

}

