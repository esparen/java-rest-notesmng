package com.br.fullstackedu.notesmng.controller.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Atributo nome_usuario é obrigatório")
        String nome_usuario,
        @NotBlank(message = "Atributo senha é obrigatório")
        String senha
) {
}
