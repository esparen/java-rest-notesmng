package com.br.fullstackedu.notesmng.controller;

import com.br.fullstackedu.notesmng.controller.dto.dtoRequest.NotaRequest;
import com.br.fullstackedu.notesmng.controller.dto.dtoResponse.NotaResponse;
import com.br.fullstackedu.notesmng.service.NotaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/notas")
@RequiredArgsConstructor
@Validated
public class NotaController {
    private final NotaService notaService;

    @GetMapping
    public ResponseEntity<NotaResponse> getAllNotas() {
        log.info("GET /notas");
        NotaResponse response = notaService.getAll();
        if (response.success()){
            log.info("GET /notas -> 200 ");
        } else {
            log.error("POST /notas -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    //GET by id
    @GetMapping("/{targetId}")
    public ResponseEntity<NotaResponse> getNotaById(@PathVariable long targetId) {
        log.info("GET /notas/{targetId}");
        NotaResponse response = notaService.getById(targetId);
        if (response.success()){
            log.info("GET /notas/{targetId} -> 200 ");
        } else {
            log.error("POST /notas/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
    //POST
    @PostMapping()
    public ResponseEntity<NotaResponse> newNota(
            @Valid @RequestBody NotaRequest notaRequest
    ) throws Exception {
        log.info("POST /notas ");
        NotaResponse response = notaService.insertNota(notaRequest);
        if (response.success()){
            log.info("POST /notas -> Registro inserido com sucesso.");
        } else {
            log.error("POST /notas -> Erro ao inserir registro: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<NotaResponse> updateNota(
            @PathVariable Long targetId,
            @Valid @RequestBody NotaRequest notaRequest)
    {
        log.info("PUT /notas");
        NotaResponse response = notaService.updateNota(targetId, notaRequest);
        if (response.success()) {
            log.info("PUT /notas -> OK ");
        } else {
            log.error("PUT /notas -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<NotaResponse> deleteNota(
            @PathVariable @NotNull(message = "ID de Docente é requerido para excluão") Long targetId)
    {
        log.info("DELETE /notas");
        NotaResponse response = notaService.deleteNota(targetId);
        if (response.success()) {
            log.info("DELETE /notas -> OK ");
        } else {
            log.error("DELETE /notas -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }


}
