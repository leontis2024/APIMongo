package com.example.leontismongo;

import com.example.leontismongo.Usuario;
import com.example.leontismongo.Avaliacao;
import com.example.leontismongo.Comentario;
import com.example.leontismongo.HistoricoObras;
import com.example.leontismongo.StatusGuia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Usuario criarUsuario(Usuario usuario) {
        mongoTemplate.save(usuario);
        return usuario;
    }

    public List<Usuario> buscarTodosUsuarios() {
        return mongoTemplate.findAll(Usuario.class);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Usuario usuario = mongoTemplate.findOne(query, Usuario.class);

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        return usuario;
    }

    public Usuario adicionarOuAtualizarComentario(Long userId, Comentario comentario) {
        Usuario usuario = buscarUsuarioPorId(userId);

        Optional<Comentario> comentarioExistente = usuario.getComentarios().stream()
                .filter(c -> c.getObraId().equals(comentario.getObraId()))
                .findFirst();

        if (comentarioExistente.isPresent()) {
            comentarioExistente.get().setTexto(comentario.getTexto());
            comentarioExistente.get().setDataComentario(comentario.getDataComentario());
        } else {
            usuario.getComentarios().add(comentario);
        }

        return mongoTemplate.save(usuario);
    }

    public Usuario adicionarOuAtualizarAvaliacao(Long userId, Avaliacao avaliacao) {
        Usuario usuario = buscarUsuarioPorId(userId);

        Optional<Avaliacao> avaliacaoExistente = usuario.getAvaliacoes().stream()
                .filter(a -> a.getObraId().equals(avaliacao.getObraId()))
                .findFirst();

        if (avaliacaoExistente.isPresent()) {
            avaliacaoExistente.get().setNota(avaliacao.getNota());
            avaliacaoExistente.get().setDataAvaliacao(avaliacao.getDataAvaliacao());
        } else {
            usuario.getAvaliacoes().add(avaliacao);
        }

        return mongoTemplate.save(usuario);
    }

    public Usuario adicionarOuAtualizarHistoricoObra(Long userId, HistoricoObras historico) {
        Usuario usuario = buscarUsuarioPorId(userId);

        Optional<HistoricoObras> historicoExistente = usuario.getHistoricoObras().stream()
                .filter(h -> h.getObraId().equals(historico.getObraId()))
                .findFirst();

        if (historicoExistente.isPresent()) {
            historicoExistente.get().setDataEscaneada(historico.getDataEscaneada());
        } else {
            usuario.getHistoricoObras().add(historico);
        }

        return mongoTemplate.save(usuario);
    }

    public Usuario adicionarOuAtualizarStatusGuia(Long userId, StatusGuia statusGuia) {
        Usuario usuario = buscarUsuarioPorId(userId);

        Optional<StatusGuia> statusGuiaExistente = usuario.getStatusGuia().stream()
                .filter(s -> s.getGuiaId().equals(statusGuia.getGuiaId()))
                .findFirst();

        if (statusGuiaExistente.isPresent()) {
            statusGuiaExistente.get().setConcluido(statusGuia.isConcluido());
            statusGuiaExistente.get().setNumeroPassoAtual(statusGuia.getNumeroPassoAtual());
        } else {
            usuario.getStatusGuia().add(statusGuia);
        }

        return mongoTemplate.save(usuario);
    }
    public void deletarUsuario(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        Usuario usuario = mongoTemplate.findOne(query, Usuario.class);

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        mongoTemplate.remove(query, Usuario.class);
    }

}
