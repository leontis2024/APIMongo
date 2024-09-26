package com.example.leontismongo;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
