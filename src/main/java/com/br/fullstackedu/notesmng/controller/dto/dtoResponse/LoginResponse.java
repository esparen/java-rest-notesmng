package com.br.fullstackedu.notesmng.controller.dto.dtoResponse;

import java.time.LocalDateTime;

public record LoginResponse(Boolean success, LocalDateTime timestamp, String message, String token, long expiresIn) {
}

