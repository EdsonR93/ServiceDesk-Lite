package com.servicedesk.lite.org.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice(basePackages = "com.servicedesk.lite.org")
public class OrgExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = ex.getMostSpecificCause().getMessage();

        if (message != null) {
            if (message.contains("organizations_slug_key")) {
                return ResponseEntity.status(CONFLICT).body(Map.of(
                    "timestamp", OffsetDateTime.now().toString(),
                    "status", 409,
                    "error", "Conflict",
                    "message", "Organization slug already exists",
                    "path", request.getRequestURI()
                ));
            } else if (message.contains("uq_membership_org_user")) {
                return ResponseEntity.status(CONFLICT).body(Map.of(
                    "timestamp", OffsetDateTime.now().toString(),
                    "status", 409,
                    "error", "Conflict",
                    "message", "User is already a member of this organization",
                    "path", request.getRequestURI()
                ));
            }
        }


        return ResponseEntity.status(CONFLICT).body(Map.of(
            "timestamp", OffsetDateTime.now().toString(),
            "status", 409,
            "error", "Conflict",
            "message", "Conflict",
            "path", request.getRequestURI()
        ));

    }

    @ExceptionHandler(OrgForbiddenException.class)
    public ResponseEntity<?> handleOrgForbiddenException(OrgForbiddenException ex, HttpServletRequest request) {
        return ResponseEntity.status(FORBIDDEN).body(Map.of(
            "timestamp", OffsetDateTime.now().toString(),
            "status", 403,
            "error", "Forbidden",
            "message", ex.getMessage(),
            "path", request.getRequestURI()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(Map.of(
            "timestamp", OffsetDateTime.now().toString(),
            "status", 404,
            "error", "Not found",
            "message", ex.getMessage(),
            "path", request.getRequestURI()
        ));
    }
}
