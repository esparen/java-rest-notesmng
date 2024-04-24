package com.br.fullstackedu.notesmng.service;

import com.br.fullstackedu.notesmng.controller.dto.dtoRequest.CadernoRequest;
import com.br.fullstackedu.notesmng.controller.dto.dtoResponse.CadernoResponse;
import com.br.fullstackedu.notesmng.database.entity.CadernoEntity;
import com.br.fullstackedu.notesmng.database.entity.UsuarioEntity;
import com.br.fullstackedu.notesmng.database.repository.CadernoRepository;
import com.br.fullstackedu.notesmng.database.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadernoService {
    private final CadernoRepository cadernoRepository;
    private final UsuarioRepository usuarioRepository;

    public CadernoResponse getAll() {
        try {
            List<CadernoEntity> cadernosList = cadernoRepository.findAll();
            if (cadernosList.isEmpty()) {
                return new CadernoResponse(false, LocalDateTime.now() , "Nenhum caderno encontrado" , null,  HttpStatus.NOT_FOUND );
            } else {
                return new CadernoResponse(true, LocalDateTime.now(), "Registros encontrados: "+ cadernosList.size(), cadernosList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Cadernos cadastrados. Erro: {}", e.getMessage());
            return new CadernoResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    //getById
    public CadernoResponse getById(Long targetId) {
        try {
            CadernoEntity caderno = cadernoRepository.findById(targetId).orElse(null);
            if (Objects.isNull(caderno)) {
                return new CadernoResponse(true, LocalDateTime.now(), "Nenhum registro encontrado com id " + targetId , null, HttpStatus.NOT_FOUND);
            } else {
                return new CadernoResponse(false, LocalDateTime.now() , "Registro encontrado." , Collections.singletonList(caderno),  HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Caderno cadastrado. Erro: {}", e.getMessage());
            return new CadernoResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public CadernoResponse insertCaderno(CadernoRequest cadernoRequest) {
        try {
            UsuarioEntity foundUser = usuarioRepository.findById(cadernoRequest.id_usuario()).orElse(null);
            log.info("foundUser: {}", foundUser);
            if (Objects.isNull(foundUser))
                return new CadernoResponse(false, LocalDateTime.now() , "Usuario id [" + cadernoRequest.id_usuario() + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            CadernoEntity newCadernoEntity = new CadernoEntity();
            newCadernoEntity.setUserEntity(foundUser);
            newCadernoEntity.setNome(cadernoRequest.nome());
            CadernoEntity savedCadernoEntity = cadernoRepository.save(newCadernoEntity);
            log.info("Caderno inserido com sucesso: {}", newCadernoEntity);
            return new CadernoResponse(true, LocalDateTime.now(),"Caderno cadastrado com sucesso.", Collections.singletonList(savedCadernoEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Falha ao adicionar Caderno. Erro: {}", e.getMessage());
            return new CadernoResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public CadernoResponse updateCaderno(Long targetId, CadernoRequest cadernoRequest) {
        try {
            CadernoEntity foundCadernoEntity = cadernoRepository.findById(targetId).orElse(null);
            if (Objects.isNull(foundCadernoEntity))
                return new CadernoResponse(false, LocalDateTime.now() , "Caderno id [" + targetId + "] não encontrado" , null, HttpStatus.NOT_FOUND);
            UsuarioEntity foundUser = usuarioRepository.findById(cadernoRequest.id_usuario()).orElse(null);
            if (Objects.isNull(foundUser))
                return new CadernoResponse(false, LocalDateTime.now() , "Usuario id [" + targetId + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            foundCadernoEntity.setUserEntity(foundUser);
            if (Objects.nonNull(cadernoRequest.nome())) foundCadernoEntity.setNome(cadernoRequest.nome());
            CadernoEntity savedCadernoEntity = cadernoRepository.save(foundCadernoEntity);
            log.info("Caderno atualizado com sucesso: {}", foundCadernoEntity);
            return new CadernoResponse(true, LocalDateTime.now(),"Caderno cadastrado com sucesso.", Collections.singletonList(savedCadernoEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Falha ao atualizar Caderno. Erro: {}", e.getMessage());
            return new CadernoResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public CadernoResponse deleteCaderno(Long targetId) {
        try {
            CadernoEntity foundCadernoEntity = cadernoRepository.findById(targetId).orElse(null);
            if (Objects.isNull(foundCadernoEntity))
                return new CadernoResponse(false, LocalDateTime.now(), "Caderno id [" + targetId + "] não encontrado", null, HttpStatus.NOT_FOUND);
            cadernoRepository.delete(foundCadernoEntity);
            log.info("Caderno removido com sucess: {}", foundCadernoEntity);
            return new CadernoResponse(true, LocalDateTime.now(),"Caderno cadastrado com sucesso.", Collections.singletonList(foundCadernoEntity), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("Falha ao remover Caderno. Erro: {}", e.getMessage());
            return new CadernoResponse(false, LocalDateTime.now(), e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

}
