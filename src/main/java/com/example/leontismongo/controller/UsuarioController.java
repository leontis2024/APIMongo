package com.example.leontismongo.controller;
import com.example.leontismongo.service.UsuarioService;
import com.example.leontismongo.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Cria um novo usuário", description = "Cria um usuário no mongo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Usuário criado com sucessp",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "500",description = "Erro interno no servidor",content = @Content)
    })
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(usuario));
    }

    @Operation(summary = "Busca todos os usuários", description = "Retorna uma lista de usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuário retornado com sucesso",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar o usuário",content = @Content),
            @ApiResponse(responseCode = "500",description = "Erro interno no servidor",content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodosUsuarios() {
        return ResponseEntity.ok(usuarioService.buscarTodosUsuarios());
    }


    @Operation(summary = "Busca usuário por ID", description = "Retorna um usuário a partir do id passado como parametro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuário retornado com sucesso",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar o usuário",content = @Content),
            @ApiResponse(responseCode = "500",description = "Erro interno no servidor",content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@Parameter(description = "Id do usuário a ser retornado")@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @Operation(summary = "Adiciona ou atualiza um comentário", description = "Se não existir adiciona um comentário para obra com id passado como parametro se não a atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Comentário inserido/atualizado com sucesso",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Comentario.class))),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar o usuário",content = @Content),
            @ApiResponse(responseCode = "500",description = "Erro interno no servidor",content = @Content)
    })
    @PutMapping("/{userId}/comentarios")
    public ResponseEntity<?> adicionarOuAtualizarComentario(@Parameter(description = "Id do usuário que comentou") @PathVariable Long userId, @RequestBody Comentario comentario) {
        try {
            return ResponseEntity.ok(usuarioService.adicionarOuAtualizarComentario(userId, comentario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }


    @Operation(summary = "Adiciona ou atualiza uma avaliação", description = "Se não existir adiciona uma avaliação para obra para o usuário com id passado como parametro se não a atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Avaliação inserida/atualizada com sucesso",content = @Content(mediaType = "application/json",schema = @Schema(implementation = Avaliacao.class))),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar o usuário",content = @Content),
            @ApiResponse(responseCode = "500",description = "Erro interno no servidor",content = @Content)
    })
    @PutMapping("/{userId}/avaliacoes")
    public ResponseEntity<?> adicionarOuAtualizarAvaliacao( @Parameter(description = "Id do usuário que avaliou")@PathVariable Long userId, @RequestBody Avaliacao avaliacao) {
        try {
            return ResponseEntity.ok(usuarioService.adicionarOuAtualizarAvaliacao(userId, avaliacao));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }


    @Operation(summary = "Adiciona ou atualiza o histórico de uma obra", description = "Se não existir adiciona como escaneada uma obra para o usuário com id passado como parametro se não atualiza a data de escaneamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Obra escaneada inserido/atualizado com sucesso",content = @Content(mediaType = "application/json",schema = @Schema(implementation = HistoricoObras.class))),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar o usuário",content = @Content),
            @ApiResponse(responseCode = "500",description = "Erro interno no servidor",content = @Content)
    })
    @PutMapping("/{userId}/historico-obras")
    public ResponseEntity<?> adicionarOuAtualizarHistoricoObra( @Parameter(description = "Id do usuário que escaneou a obra")@PathVariable Long userId, @RequestBody HistoricoObras historico) {
        try {
            return ResponseEntity.ok(usuarioService.adicionarOuAtualizarHistoricoObra(userId, historico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }


    @Operation(summary = "Adiciona ou atualiza o status de um guia", description = "Se não existir adiciona o status de um guia para um usuário com id passado como parametro se não o atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Status Guia inserido/atualizado com sucesso",content = @Content(mediaType = "application/json",schema = @Schema(implementation = StatusGuia.class))),
            @ApiResponse(responseCode = "404",description = "Não foi possível encontrar o usuário",content = @Content),
            @ApiResponse(responseCode = "500",description = "Erro interno no servidor",content = @Content)
    })
    @PutMapping("/{userId}/status-guia")
    public ResponseEntity<?> adicionarOuAtualizarStatusGuia( @Parameter(description = "Id do usuário que fez/começou um guia")@PathVariable Long userId, @RequestBody StatusGuia statusGuia) {
        try {
            return ResponseEntity.ok(usuarioService.adicionarOuAtualizarStatusGuia(userId, statusGuia));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
    @Operation(summary = "Deleta um usuário por ID", description = "Deleta um usuário com o ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso",content = @Content(mediaType = "application/json",schema = @Schema(implementation = StatusGuia.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@Parameter(description = "ID do usuário a ser deletado") @PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.ok("Usuário deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
    @Operation(summary = "Busca usuários que comentaram em uma obra", description = "Retorna uma lista de usuários que comentaram em uma obra específica pelo ID da obra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentários de usuários retornados com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Nenhum comentário encontrado para a obra", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/comentarios/{obraId}")
    public ResponseEntity<?> obtrUsuariosByComentarioObraId(@Parameter(description = "ID da obra", example = "12345")@PathVariable Long obraId) {
        List<ComentarioUsuario> comentarios = usuarioService.obterComentariosPorObraId(obraId);
        if (comentarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum comentário encontrado para a obra.");
        }
        return ResponseEntity.ok(comentarios);
    }
    @Operation(summary = "Obter média de notas de uma obra", description = "Retorna a média de notas de uma obra pelo ID da obra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Média de notas calculada com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Nenhuma avaliação encontrada para a obra", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/media-nota/{obraId}")
    public ResponseEntity<?> obterMediaNota(@Parameter(description = "ID da obra", example = "12345")@PathVariable Long obraId) {
        Double mediaNota = usuarioService.calcularMediaNotaPorObraId(obraId);
        if (mediaNota == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma avaliação encontrada para a obra.");
        }
        return ResponseEntity.ok(mediaNota);
    }
    @Operation(summary = "Obter status de guia de um usuário", description = "Retorna o status de um guia para um usuário pelo ID do usuário e ID do guia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status de guia retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatusGuiaMostrar.class))),
            @ApiResponse(responseCode = "404", description = "Status de guia não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/{usuarioId}/status-guia/{guiaId}")
    public ResponseEntity<?> obterStatusGuia(@Parameter(description = "ID do usuário", example = "12345")@PathVariable Long usuarioId,@Parameter(description = "ID do guia", example = "12345") @PathVariable Long guiaId) {
        Optional<StatusGuiaMostrar> statusGuia = usuarioService.obterStatusGuia(usuarioId, guiaId);
        if (statusGuia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status do guia não encontrado para este usuário.");
        }
        return ResponseEntity.ok(statusGuia.get());
    }
    @Operation(summary = "Obter histórico de uma obra de um usuário", description = "Retorna o histórico de escaneamento de uma obra para um usuário pelo ID do usuário e ID da obra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de obra retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoricoObras.class))),
            @ApiResponse(responseCode = "404", description = "Histórico de obra não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/{usuarioId}/historico/{obraId}")
    public ResponseEntity<?> obterHistoricoObra(@Parameter(description = "ID do usuário", example = "12345")@PathVariable Long usuarioId,@Parameter(description = "ID da obra", example = "12345") @PathVariable Long obraId) {
        Optional<HistoricoObras> historicoObra = usuarioService.obterHistoricoObra(usuarioId, obraId);
        if (historicoObra.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Histórico da obra não encontrado para este usuário.");
        }
        return ResponseEntity.ok(historicoObra.get());
    }

    @Operation(summary = "Obter histórico de obras de um usuário", description = "Retorna o histórico completo de obras escaneadas por um usuário pelo ID do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de obras retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoricoObras.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/{id}/historicoObras")
    public ResponseEntity<?> obterHistoricoObras( @Parameter(description = "ID do usuário", example = "12345")@PathVariable Long id) {
        Optional<List<HistoricoObras>> historicoObras = usuarioService.buscarHistoricoObrasPorId(id);
        if (historicoObras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado ou sem histórico de obras.");
        }
        return ResponseEntity.ok(historicoObras.get());
    }

    @Operation(summary = "Conta a porcentagem de avaliações em uma obra com base na faixa de notas",
            description = "Calcula a porcentagem de avaliações que estão dentro de uma faixa de notas para uma obra específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Porcentagem de avaliações retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "number", format = "double"))),
            @ApiResponse(responseCode = "404", description = "Obra não encontrada ou não possui avaliações",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content)
    })
    @GetMapping("/porcentagem")
    public ResponseEntity<Double> contarAvaliacoesPorObraIdENotaComPorcentagem(
            @Parameter(description = "ID da obra para a qual as avaliações serão contadas",
                    example = "123") @RequestParam Long obraId,
            @Parameter(description = "Nota mínima para considerar a avaliação",
                    example = "4.0") @RequestParam double notaMinima,
            @Parameter(description = "Nota máxima para considerar a avaliação",
                    example = "5.0") @RequestParam double notaMaxima) {
        try {
            double porcentagem = usuarioService.contarAvaliacoesPorObraIdENotaComPorcentagem(obraId, notaMinima, notaMaxima);
            return ResponseEntity.ok(porcentagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Buscar comentários de um usuário",
            description = "Retorna uma lista de comentários feitos por um usuário específico com base no ID do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentários retornados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comentario.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum comentário encontrado para o usuário", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/{userId}/comentarios")
    public ResponseEntity<?> buscarComentariosPorUsuarioId(
            @Parameter(description = "ID do usuário para buscar os comentários") @PathVariable Long userId) {
        List<Comentario> comentarios = usuarioService.buscarComentariosPorUsuarioId(userId);
        if (comentarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum comentário encontrado para o usuário.");
        }
        return ResponseEntity.ok(comentarios);
    }

    // Buscar Notas (Avaliações) por ID do Usuário
    @Operation(summary = "Buscar avaliações de um usuário",
            description = "Retorna uma lista de avaliações (notas) feitas por um usuário específico com base no ID do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações retornadas com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Avaliacao.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma avaliação encontrada para o usuário", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping("/{userId}/avaliacoes")
    public ResponseEntity<?> buscarAvaliacoesPorUsuarioId(
            @Parameter(description = "ID do usuário para buscar as avaliações") @PathVariable Long userId) {
        List<Avaliacao> avaliacoes = usuarioService.buscarAvaliacoesPorUsuarioId(userId);
        if (avaliacoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma avaliação encontrada para o usuário.");
        }
        return ResponseEntity.ok(avaliacoes);
    }
    @Operation(summary = "Atualiza se o usuário é elegível para premium", description = "Atualiza o status de elegibilidade para premium de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PutMapping("/{userId}/premium")
    public ResponseEntity<?> atualizarPossivelPremium(
            @Parameter(description = "ID do usuário a ser atualizado") @PathVariable Long userId,
            @Parameter(description = "Valor de elegibilidade para premium") @Valid @RequestParam Boolean possivelPremium) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarPossivelPremium(userId, possivelPremium);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }
    }
}
