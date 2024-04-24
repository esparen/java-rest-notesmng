package com.br.fullstackedu.notesmng.database.repository;

import com.br.fullstackedu.notesmng.database.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
    List<NotaEntity> findAllByUsuarioId(Long usuarioId);
    Optional<NotaEntity> findByIdAndUsuarioId(Long notaId, Long usuarioId);

}
