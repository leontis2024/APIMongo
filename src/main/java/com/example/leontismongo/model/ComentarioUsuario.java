package com.example.leontismongo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ComentarioUsuario {
    @Schema(description = "ID do usuário que fez o comentário", example = "12345")
    private Long usuarioId;
    @Schema(description = "Comentário associado ao usuário", implementation = Comentario.class)
    private Comentario comentario;
}
