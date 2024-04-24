package com.br.fullstackedu.notesmng.controller.dto.dtoRequest;

import jakarta.validation.constraints.NotNull;

public record NotaRequest(
        @NotNull(message = "Atributo title é obrigatório")
        String title,

        @NotNull(message = "Atributo content é obrigatório")
        String content,

        @NotNull(message = "Atributo id_caderno é obrigatório")
        Long id_caderno,

        @NotNull(message = "Atributo id_usuario é obrigatorio")
        Long id_usuario
) {
}
