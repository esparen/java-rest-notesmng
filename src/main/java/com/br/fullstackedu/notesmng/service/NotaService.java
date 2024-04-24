package com.br.fullstackedu.notesmng.service;

import com.br.fullstackedu.notesmng.controller.dto.dtoRequest.NotaRequest;
import com.br.fullstackedu.notesmng.controller.dto.dtoResponse.NotaResponse;
import com.br.fullstackedu.notesmng.database.entity.CadernoEntity;
import com.br.fullstackedu.notesmng.database.entity.NotaEntity;
import com.br.fullstackedu.notesmng.database.entity.UsuarioEntity;
import com.br.fullstackedu.notesmng.database.repository.CadernoRepository;
import com.br.fullstackedu.notesmng.database.repository.NotaRepository;
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
public class NotaService {
    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CadernoRepository cadernoRepository;
    private final LoginService loginService;

    public NotaResponse getAll(String actualToken) {
        try {
            Long ownerId = Long.valueOf(loginService.getFieldInToken(actualToken, "usuario_id"));
            List<NotaEntity> notasList = notaRepository.findAllByUsuarioId(ownerId);
            if (notasList.isEmpty()) {
                return new NotaResponse(false, LocalDateTime.now() , "Nenhuma nota encontrado que pertença ao usuário logado" , null,  HttpStatus.NOT_FOUND );
            } else {
                return new NotaResponse(true, LocalDateTime.now(), "Registros encontrados: "+ notasList.size(), notasList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Notas cadastrados. Erro: {}", e.getMessage());
            return new NotaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    //getById
    public NotaResponse getById(Long targetId, String actualToken) {
        try {
            Long ownerId = Long.valueOf(loginService.getFieldInToken(actualToken, "usuario_id"));
            NotaEntity nota = notaRepository.findByIdAndUsuarioId(targetId, ownerId).orElse(null);
            if (Objects.isNull(nota)) {
                return new NotaResponse(true, LocalDateTime.now(), "Nenhum registro encontrado com id " + targetId + " que pertença ao usuário logado" , null, HttpStatus.NOT_FOUND);
            } else {
                return new NotaResponse(false, LocalDateTime.now() , "Registro encontrado." , Collections.singletonList(nota),  HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Falha ao buscar Nota cadastrado. Erro: {}", e.getMessage());
            return new NotaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public NotaResponse insertNota(NotaRequest notaRequest) {
        try {
            UsuarioEntity foundUser = usuarioRepository.findById(notaRequest.id_usuario()).orElse(null);
            if (Objects.isNull(foundUser))
                return new NotaResponse(false, LocalDateTime.now() , "Usuario id [" + notaRequest.id_usuario() + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            CadernoEntity foundCaderno = cadernoRepository.findById(notaRequest.id_caderno()).orElse(null);
            if (Objects.isNull(foundCaderno))
                return new NotaResponse(false, LocalDateTime.now() , "Caderno id [" + notaRequest.id_usuario() + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            NotaEntity newNotaEntity = new NotaEntity();
            newNotaEntity.setUsuario(foundUser);
            newNotaEntity.setCaderno(foundCaderno);
            newNotaEntity.setTitle(notaRequest.title());
            newNotaEntity.setContent(notaRequest.content());
            NotaEntity savedNotaEntity = notaRepository.save(newNotaEntity);
            log.info("Nota inserido com sucesso: {}", newNotaEntity);
            return new NotaResponse(true, LocalDateTime.now(),"Nota cadastrado com sucesso.", Collections.singletonList(savedNotaEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Falha ao adicionar Nota. Erro: {}", e.getMessage());
            return new NotaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public NotaResponse updateNota(Long targetId, NotaRequest notaRequest, String actualToken) {
        try {
            Long ownerId = Long.valueOf(loginService.getFieldInToken(actualToken, "usuario_id"));
            NotaEntity foundNotaEntity = notaRepository.findByIdAndUsuarioId(targetId, ownerId).orElse(null);
            if (Objects.isNull(foundNotaEntity))
                return new NotaResponse(false, LocalDateTime.now() , "Nota id [" + targetId + "] não encontrado ou esta não pertence ao usuário logado" , null, HttpStatus.NOT_FOUND);

            UsuarioEntity foundUser = usuarioRepository.findById(notaRequest.id_usuario()).orElse(null);
            if (Objects.isNull(foundUser))
                return new NotaResponse(false, LocalDateTime.now() , "Usuario id [" + notaRequest.id_usuario() + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            CadernoEntity foundCaderno = cadernoRepository.findById(notaRequest.id_caderno()).orElse(null);
            if (Objects.isNull(foundCaderno))
                return new NotaResponse(false, LocalDateTime.now() , "Caderno id [" + notaRequest.id_usuario() + "] não encontrado" , null, HttpStatus.NOT_FOUND);

            foundNotaEntity.setUsuario(foundUser);
            foundNotaEntity.setCaderno(foundCaderno);
            if (Objects.nonNull(notaRequest.title())) foundNotaEntity.setTitle(notaRequest.title());
            if (Objects.nonNull(notaRequest.content())) foundNotaEntity.setContent(notaRequest.content());
            NotaEntity savedNotaEntity = notaRepository.save(foundNotaEntity);
            log.info("Nota atualizado com sucesso: {}", foundNotaEntity);
            return new NotaResponse(true, LocalDateTime.now(),"Nota atualizada com sucesso.", Collections.singletonList(savedNotaEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.info("Falha ao atualizar Nota. Erro: {}", e.getMessage());
            return new NotaResponse(false, LocalDateTime.now() , e.getMessage() , null, HttpStatus.BAD_REQUEST );
        }
    }

    public NotaResponse deleteNota(Long targetId, String actualToken) {
        try {
            Long ownerId = Long.valueOf(loginService.getFieldInToken(actualToken, "usuario_id"));
            NotaEntity foundNotaEntity = notaRepository.findByIdAndUsuarioId(targetId, ownerId).orElse(null);
            if (Objects.isNull(foundNotaEntity))
                return new NotaResponse(false, LocalDateTime.now(), "Nota id [" + targetId + "] não encontrada ou esta não pertence ao usuário logado", null, HttpStatus.NOT_FOUND);
            notaRepository.delete(foundNotaEntity);
            log.info("Nota removido com sucess: {}", foundNotaEntity);
            return new NotaResponse(true, LocalDateTime.now(),"Nota removida com sucesso.", Collections.singletonList(foundNotaEntity), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.info("Falha ao remover Nota. Erro: {}", e.getMessage());
            return new NotaResponse(false, LocalDateTime.now(), e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }
}
