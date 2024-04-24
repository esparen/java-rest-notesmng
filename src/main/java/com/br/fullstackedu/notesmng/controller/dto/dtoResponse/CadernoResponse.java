package com.br.fullstackedu.notesmng.controller.dto.dtoResponse;

import com.br.fullstackedu.notesmng.database.entity.CadernoEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


public record CadernoResponse(Boolean success, LocalDateTime timestamp, String message, List<CadernoEntity> cadernoData, HttpStatus httpStatus) {
}

