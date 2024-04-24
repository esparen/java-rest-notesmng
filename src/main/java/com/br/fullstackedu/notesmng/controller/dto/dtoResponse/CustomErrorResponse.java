package com.br.fullstackedu.notesmng.controller.dto.dtoResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
public class CustomErrorResponse {
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String trace;

    public CustomErrorResponse(int status, LocalDateTime timestamp, String message, String trace) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.trace = trace;
    }
}