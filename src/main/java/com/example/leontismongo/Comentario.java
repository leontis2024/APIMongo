package com.example.leontismongo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Comentario {

    private String obraId;
    private String texto;
    private LocalDateTime dataComentario;

}

