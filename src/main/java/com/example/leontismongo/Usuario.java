package com.example.leontismongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
@Getter
@Setter
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private String nome;
    private String email;
    private List<Comentario> comentarios;
    private List<Avaliacao> avaliacoes;
    private List<HistoricoObras> historicoObras;
    private List<StatusGuia> statusGuia;

}
