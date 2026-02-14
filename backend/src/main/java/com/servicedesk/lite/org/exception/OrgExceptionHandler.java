package com.servicedesk.lite.org.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice(basePackages = "com.servicedesk.lite.org")
public class OrgExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> createOrganization(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = ex.getMostSpecificCause().getMessage();

        if(message != null && message.contains("organizations_slug_key")){
            return ResponseEntity.status(CONFLICT).body(Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 409,
                "error","Conflict",
                "message", "Organization slug already exists",
                "path",request.getRequestURI()
            ));
        }

        return ResponseEntity.status(CONFLICT).body(Map.of(
            "timestamp", OffsetDateTime.now().toString(),
            "status", 409,
            "error","Conflict",
            "message", "Conflict",
            "path",request.getRequestURI()
        ));

    }
}
