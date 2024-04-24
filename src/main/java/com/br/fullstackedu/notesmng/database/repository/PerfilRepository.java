package com.br.fullstackedu.notesmng.database.repository;

import com.br.fullstackedu.notesmng.database.entity.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {

    Optional<PerfilEntity> findByNome(String nomePerfil);

}
