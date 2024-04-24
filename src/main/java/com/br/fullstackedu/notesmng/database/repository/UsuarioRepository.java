package com.br.fullstackedu.notesmng.database.repository;

import com.br.fullstackedu.notesmng.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

}
