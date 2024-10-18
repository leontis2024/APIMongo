package com.example.leontismongo.service;

import com.example.leontismongo.model.*;
import com.example.leontismongo.repository.UsuarioRepository;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;


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


    public List<ComentarioUsuario> obterComentariosPorObraId(Long obraId) {

        List<Usuario> usuarios = usuarioRepository.findComentarioByObraId(obraId);
        List<ComentarioUsuario> comentariosFiltrados = new ArrayList<>();


        for (Usuario usuario : usuarios) {
            for (Comentario comentario : usuario.getComentarios()) {

                if (comentario.getObraId().equals(obraId)) {

                    comentariosFiltrados.add(new ComentarioUsuario(usuario.getId(), comentario));
                }
            }
        }

        return comentariosFiltrados;
    }

    public Double calcularMediaNotaPorObraId(Long obraId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("avaliacoes.obraId").is(obraId)),
                Aggregation.unwind("avaliacoes"),
                Aggregation.match(Criteria.where("avaliacoes.obraId").is(obraId)),
                Aggregation.group().avg("avaliacoes.nota").as("mediaNota")
        );

        AggregationResults<MediaNota> results = mongoTemplate.aggregate(aggregation, "usuarios", MediaNota.class);
        return results.getMappedResults().isEmpty() ? null : results.getMappedResults().get(0).getMediaNota();
    }

    public Optional<StatusGuiaMostrar> obterStatusGuia(Long usuarioId, Long guiaId) {
        return usuarioRepository.findStatusGuiaByUsuarioIdAndGuiaId(usuarioId, guiaId);
    }
    public Optional<HistoricoObras> obterHistoricoObra(Long usuarioId, Long obraId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findHistoricoObraByUsuarioIdAndObraId(usuarioId, obraId);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();


            for (HistoricoObras historico : usuario.getHistoricoObras()) {
                if (historico.getObraId().equals(obraId)) {
                    return Optional.of(historico);
                }
            }
        }

        return Optional.empty();
    }

    public Optional<List<HistoricoObras>> buscarHistoricoObrasPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(Usuario::getHistoricoObras);
    }

    public double contarAvaliacoesPorObraIdENotaComPorcentagem(Long obraId, double notaMinima, double notaMaxima) {
        Aggregation totalAvaliacoesAgg = Aggregation.newAggregation(
                Aggregation.unwind("avaliacoes"),
                Aggregation.match(Criteria.where("avaliacoes.obraId").is(obraId)),
                Aggregation.group().count().as("totalAvaliacoes")
        );

        AggregationResults<DBObject> totalResults = mongoTemplate.aggregate(totalAvaliacoesAgg, "usuarios", DBObject.class);
        long totalAvaliacoes = totalResults.getMappedResults().isEmpty() ? 0 : ((Number) totalResults.getMappedResults().get(0).get("totalAvaliacoes")).longValue();

        if (totalAvaliacoes == 0) {
            return 0.0;
        }

        Aggregation faixaAvaliacoesAgg = Aggregation.newAggregation(
                Aggregation.unwind("avaliacoes"),
                Aggregation.match(Criteria.where("avaliacoes.obraId").is(obraId)
                        .and("avaliacoes.nota").gte(notaMinima).lt(notaMaxima)),
                Aggregation.group().count().as("quantidadeAvaliacoes")
        );

        AggregationResults<DBObject> faixaResults = mongoTemplate.aggregate(faixaAvaliacoesAgg, "usuarios", DBObject.class);
        long quantidadeAvaliacoesNaFaixa = faixaResults.getMappedResults().isEmpty() ? 0 : ((Number) faixaResults.getMappedResults().get(0).get("quantidadeAvaliacoes")).longValue();

        return (quantidadeAvaliacoesNaFaixa * 100.0) / totalAvaliacoes;
    }

}

