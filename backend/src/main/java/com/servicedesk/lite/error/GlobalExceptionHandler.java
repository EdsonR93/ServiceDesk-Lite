package com.servicedesk.lite.error;

import com.servicedesk.lite.auth.exception.EmailAlreadyExistsException;
import com.servicedesk.lite.auth.exception.InvalidCredentialsException;
import com.servicedesk.lite.org.exception.OrgForbiddenException;
import com.servicedesk.lite.user.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Auth exceptions
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(Map.of(
            "timestamp", OffsetDateTime.now().toString(),
            "status", 409,
            "error", "Conflict",
            "message", ex.getMessage(),
            "path", request.getRequestURI()
        ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        return ResponseEntity.status(UNAUTHORIZED).body(Map.of(
            "timestamp", OffsetDateTime.now().toString(),
            "status", 401,
            "error", "Unauthorized",
            "message", ex.getMessage(),
            "path", request.getRequestURI()
        ));
    }

    //Org and Membership exceptions
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

    @ExceptionHandler(ResponseStatusException.class)
    public ApiErrorResponse handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        return new ApiErrorResponse(
            OffsetDateTime.now(),
            ex.getStatusCode().value(),
            Objects.requireNonNull(resolve(ex.getStatusCode().value())).getReasonPhrase(),
            ex.getReason(),
            request.getRequestURI(),
            List.of()
        );
    }

    private ApiErrorResponse mapToApiErrorResponse(ResponseStatusException ex, HttpServletRequest req) {
        HttpStatus statusCode = HttpStatus.resolve(ex.getStatusCode().value());

        String errorType = statusCode != null
            ? statusCode.getReasonPhrase()
            : "Error";

        String message = ex.getReason() != null
            ? ex.getReason()
            : errorType;

        return new ApiErrorResponse(
            OffsetDateTime.now(),
            ex.getStatusCode().value(),
            errorType,
            message,
            req.getRequestURI(),
            List.of()
        );
    }

    private ApiErrorResponse buildErrorResponse(HttpStatus status, String errorMessage, HttpServletRequest req) {
        String errorType = status != null
            ? status.getReasonPhrase()
            : "Internal Server Error";
        int statusCode = status != null ? status.value() : 500;

        String message = (errorMessage != null) ? errorMessage : errorType;

        return new ApiErrorResponse(
            OffsetDateTime.now(),
            statusCode,
            errorType,
            message,
            req.getRequestURI(),
            List.of()
        );
    }
}
