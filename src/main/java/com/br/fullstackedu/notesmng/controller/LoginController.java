package com.br.fullstackedu.notesmng.controller;

import com.br.fullstackedu.notesmng.controller.dto.dtoRequest.LoginRequest;
import com.br.fullstackedu.notesmng.controller.dto.dtoResponse.LoginResponse;
import com.br.fullstackedu.notesmng.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;
    private final BCryptPasswordEncoder bCryptEncoder;


    @PostMapping()
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        log.info("POST /login -> {}",loginRequest.nome_usuario());
        LoginResponse loginResponse = loginService.doLogin(loginRequest);
        if (loginResponse.success()) {
            log.info("POST /login -> Sucesso na autenticação de {}",loginRequest.nome_usuario());
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } else {
            log.warn("POST /login -> Falha na autenticação de {}",loginRequest.nome_usuario());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }
}
