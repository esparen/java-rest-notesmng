package com.br.fullstackedu.notesmng.controller;

import com.br.fullstackedu.notesmng.controller.dto.dtoRequest.CadernoRequest;
import com.br.fullstackedu.notesmng.controller.dto.dtoResponse.CadernoResponse;
import com.br.fullstackedu.notesmng.service.CadernoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/cadernos")
@RequiredArgsConstructor
@Validated
public class CadernoController {
    private final CadernoService cadernoService;


    @GetMapping
    public ResponseEntity<CadernoResponse> getAllCadernos() {
        log.info("GET /cadernos");
        CadernoResponse response = cadernoService.getAll();
        if (response.success()){
            log.info("GET /cadernos -> 200 ");
        } else {
            log.error("POST /cadernos -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    //GET by id
    @GetMapping("/{targetId}")
    public ResponseEntity<CadernoResponse> getCadernoById(@PathVariable long targetId) {
        log.info("GET /cadernos/{targetId}");
        CadernoResponse response = cadernoService.getById(targetId);
        if (response.success()){
            log.info("GET /cadernos/{targetId} -> 200 ");
        } else {
            log.error("POST /cadernos/{targetId} -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
    //POST
    @PostMapping()
    public ResponseEntity<CadernoResponse> newCaderno(
            @Valid @RequestBody CadernoRequest cadernoRequest
    ) throws Exception {
        log.info("POST /cadernos ");
        CadernoResponse response = cadernoService.insertCaderno(cadernoRequest);
        if (response.success()){
            log.info("POST /cadernos -> Registro inserido com sucesso.");
        } else {
            log.error("POST /cadernos -> Erro ao inserir registro: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<CadernoResponse> updateCaderno(
            @PathVariable Long targetId,
            @Valid @RequestBody CadernoRequest cadernoRequest)
    {
        log.info("PUT /cadernos");
        CadernoResponse response = cadernoService.updateCaderno(targetId, cadernoRequest);
        if (response.success()) {
            log.info("PUT /cadernos -> OK ");
        } else {
            log.error("PUT /cadernos -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<CadernoResponse> deleteCaderno(
            @PathVariable @NotNull(message = "ID de Docente é requerido para excluão") Long targetId)
    {
        log.info("DELETE /cadernos");
        CadernoResponse response = cadernoService.deleteCaderno(targetId);
        if (response.success()) {
            log.info("DELETE /cadernos -> OK ");
        } else {
            log.error("DELETE /cadernos -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

}
