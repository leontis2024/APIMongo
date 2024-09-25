package com.example.leontismongo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "usuarios")
public class Usuario {

    @Id
    @Schema(description = "ID único do usuário", example = "12345")
    private Long id;

    @Schema(description = "Nome do usuário", example = "Ana")
    private String nome;

    @Schema(description = "Email do usuário", example = "anaromera@gmail.com")
    private String email;

    private List<Comentario> comentarios = new ArrayList<>();
    private List<Avaliacao> avaliacoes = new ArrayList<>();
    private List<HistoricoObras> historicoObras = new ArrayList<>();
    private List<StatusGuia> statusGuia = new ArrayList<>();
}
