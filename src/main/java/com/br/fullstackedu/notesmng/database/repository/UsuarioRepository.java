package com.br.fullstackedu.notesmng.database.repository;

import com.br.fullstackedu.notesmng.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByNomeUsuario(String nomeUsuario);
}
