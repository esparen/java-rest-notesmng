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
    public ResponseEntity<CadernoResponse> getAllCadernos(
            @RequestHeader(name = "Authorization") String requestToken
    ) {
        log.info("GET /cadernos");
        String actualToken = requestToken.substring(7);
        CadernoResponse response = cadernoService.getAll(actualToken);
        if (response.success()){
            log.info("GET /cadernos -> 200 ");
        } else {
            log.error("POST /cadernos -> Erro ao buscar registros: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    //GET by id
    @GetMapping("/{targetId}")
    public ResponseEntity<CadernoResponse> getCadernoById(
            @PathVariable long targetId,
            @RequestHeader(name = "Authorization") String requestToken
            ) {
        log.info("GET /cadernos/{targetId}");
        String actualToken = requestToken.substring(7);
        CadernoResponse response = cadernoService.getById(targetId, actualToken);
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
            @Valid @RequestBody CadernoRequest cadernoRequest,
            @RequestHeader(name = "Authorization") String requestToken
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
            @Valid @RequestBody CadernoRequest cadernoRequest,
            @RequestHeader(name = "Authorization") String requestToken)
    {
        log.info("PUT /cadernos");
        String actualToken = requestToken.substring(7);
        CadernoResponse response = cadernoService.updateCaderno(targetId, cadernoRequest, actualToken);
        if (response.success()) {
            log.info("PUT /cadernos -> OK ");
        } else {
            log.error("PUT /cadernos -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<CadernoResponse> deleteCaderno(
            @PathVariable @NotNull(message = "ID de Docente é requerido para excluão") Long targetId,
            @RequestHeader(name = "Authorization") String requestToken
    )
    {
        log.info("DELETE /cadernos");
        String actualToken = requestToken.substring(7);
        CadernoResponse response = cadernoService.deleteCaderno(targetId, actualToken);
        if (response.success()) {
            log.info("DELETE /cadernos -> OK ");
        } else {
            log.error("DELETE /cadernos -> 400");
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }

}
