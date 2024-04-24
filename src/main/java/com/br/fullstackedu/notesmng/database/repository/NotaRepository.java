package com.br.fullstackedu.notesmng.database.repository;

import com.br.fullstackedu.notesmng.database.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<NotaEntity, Long> {

}
