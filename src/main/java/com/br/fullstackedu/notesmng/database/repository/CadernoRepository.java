package com.br.fullstackedu.notesmng.database.repository;


import com.br.fullstackedu.notesmng.database.entity.CadernoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CadernoRepository extends JpaRepository<CadernoEntity, Long> {
    List<CadernoEntity> findAllByUserId(Long userId);
    Optional<CadernoEntity> findByUserId(Long userId);
    Optional<CadernoEntity> findByIdAndUserId(Long id, Long userId);

}
