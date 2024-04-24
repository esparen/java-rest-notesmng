package com.br.fullstackedu.notesmng.controller;

import com.br.fullstackedu.notesmng.controller.dto.dtoRequest.CadastroRequest;
import com.br.fullstackedu.notesmng.controller.dto.dtoResponse.CadastroResponse;
import com.br.fullstackedu.notesmng.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cadastro")
@Validated
public class CadastroController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<CadastroResponse> newUser(
            @Valid @RequestBody CadastroRequest cadastroRequest
    ) throws Exception {
        log.info("POST /cadastro ");
        CadastroResponse response = loginService.createUser(cadastroRequest);
        if (response.success()){
            log.info("POST /cadastro -> Registro inserido com sucesso.");
        } else {
            log.error("POST /cadastro -> Erro ao inserir registro: [{}].", response.message());
        }
        return ResponseEntity.status(response.httpStatus()).body(response);
    }
}
