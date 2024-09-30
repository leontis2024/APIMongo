package com.example.leontismongo.repository;

import com.example.leontismongo.model.StatusGuiaMostrar;
import com.example.leontismongo.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, Long> {
    @Query(value="{ 'comentarios.obraId' : ?0 }")
    public List<Usuario> findComentarioByObraId(Long obraId);
    @Query(value = "{ '_id': ?0, 'statusGuia.guiaId': ?1 }", fields = "{ 'statusGuia.$': 1, '_id': 0 }")
    Optional<StatusGuiaMostrar> findStatusGuiaByUsuarioIdAndGuiaId(Long usuarioId, Long guiaId);
    @Query(value = "{ '_id': ?0, 'historicoObras.obraId': ?1 }", fields = "{ 'historicoObras.$': 1 }")
    Optional<Usuario> findHistoricoObraByUsuarioIdAndObraId(Long usuarioId, Long obraId);
}
