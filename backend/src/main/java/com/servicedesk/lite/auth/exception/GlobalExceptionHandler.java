package com.servicedesk.lite.auth.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(Map.of(
            "timestamp", OffsetDateTime.now().toString(),
            "status", 409,
            "error","Conflict",
            "message", ex.getMessage(),
            "path",request.getRequestURI()
        ));
    }
}
