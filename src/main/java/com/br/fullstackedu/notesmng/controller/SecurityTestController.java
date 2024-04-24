package com.br.fullstackedu.notesmng.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class SecurityTestController {

    @Secured("false")
    @GetMapping
    public String teste() {
        return "TESTE";
    }

}
