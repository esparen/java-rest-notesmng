package com.br.fullstackedu.notesmng.controller.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadernoRequest(
        @NotBlank(message = "Atributo nome é obrigatório")
        String nome,

        @NotNull(message = "Atributo id_usuario é obrigatorio")
        Long id_usuario
) {
}
