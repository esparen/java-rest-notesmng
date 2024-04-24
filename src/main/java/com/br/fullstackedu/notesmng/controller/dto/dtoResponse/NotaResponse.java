package com.br.fullstackedu.notesmng.controller.dto.dtoResponse;

import com.br.fullstackedu.notesmng.database.entity.NotaEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


public record NotaResponse(Boolean success, LocalDateTime timestamp, String message, List<NotaEntity> notaData, HttpStatus httpStatus) {
}

