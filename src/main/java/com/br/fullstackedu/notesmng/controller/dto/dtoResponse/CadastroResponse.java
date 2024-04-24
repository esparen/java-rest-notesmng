package com.br.fullstackedu.notesmng.controller.dto.dtoResponse;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record CadastroResponse(Boolean success, LocalDateTime timestamp, String message, HttpStatus httpStatus) {
}
