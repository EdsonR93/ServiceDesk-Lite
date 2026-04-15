package com.servicedesk.lite.error;

import com.servicedesk.lite.auth.exception.EmailAlreadyExistsException;
import com.servicedesk.lite.auth.exception.InvalidCredentialsException;
import com.servicedesk.lite.org.exception.OrgForbiddenException;
import com.servicedesk.lite.org.exception.OrganizationNotFoundException;
import com.servicedesk.lite.tickets.exceptions.InvalidAssigneeOperationException;
import com.servicedesk.lite.tickets.exceptions.InvalidStatusTransitionException;
import com.servicedesk.lite.tickets.exceptions.TicketNotFoundException;
import com.servicedesk.lite.user.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Auth exceptions
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(
            buildErrorResponse(CONFLICT, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        return ResponseEntity.status(UNAUTHORIZED).body(
            buildErrorResponse(UNAUTHORIZED, ex.getMessage(), request)
        );
    }

    //Org and Membership exceptions
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = ex.getMostSpecificCause().getMessage();

        if (message != null) {
            if (message.contains("organizations_slug_key")) {
                return ResponseEntity.status(CONFLICT).body(
                    buildErrorResponse(CONFLICT, "Organization slug already exists", request
                    ));
            } else if (message.contains("uq_membership_org_user")) {
                return ResponseEntity.status(CONFLICT).body(
                    buildErrorResponse(CONFLICT, "User is already a member of the organization", request)
                );
            }
        }
        return ResponseEntity.status(CONFLICT).body(
            buildErrorResponse(CONFLICT, "Data integrity violation", request)
        );
    }

    @ExceptionHandler(OrgForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleOrgForbiddenException(OrgForbiddenException ex, HttpServletRequest request) {
        return ResponseEntity.status(FORBIDDEN).body(
            buildErrorResponse(FORBIDDEN, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(
            buildErrorResponse(NOT_FOUND, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, HttpServletRequest request) {
        return ResponseEntity.status(UNAUTHORIZED).body(
            buildErrorResponse(UNAUTHORIZED, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(UserInactiveException.class)
    public ResponseEntity<ApiErrorResponse> handleUserInactiveException(UserInactiveException ex, HttpServletRequest request) {
        return ResponseEntity.status(FORBIDDEN).body(
            buildErrorResponse(FORBIDDEN, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(UserNotInOrganizationException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotInOrganizationException(UserNotInOrganizationException ex, HttpServletRequest request) {
        return ResponseEntity.status(FORBIDDEN).body(
            buildErrorResponse(FORBIDDEN, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(AssigneeNotActiveException.class)
    public ResponseEntity<ApiErrorResponse> handleAssigneeNotActiveException(AssigneeNotActiveException ex, HttpServletRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(
            buildErrorResponse(BAD_REQUEST, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleOrganizationNotFoundException(OrganizationNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(
            buildErrorResponse(NOT_FOUND, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTicketNotFoundException(TicketNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND).body(
            buildErrorResponse(NOT_FOUND, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(InvalidAssigneeOperationException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidAssigneeOperationException(InvalidAssigneeOperationException ex, HttpServletRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(
            buildErrorResponse(BAD_REQUEST, ex.getMessage(), request)
        );
    }

    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidStatusTransitionException(InvalidStatusTransitionException ex, HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(
            buildErrorResponse(CONFLICT, ex.getMessage(), request)
        );
    }

    // TEMPORARY FALLBACK:
    // Handles legacy ResponseStatusException usage during migration to custom domain exceptions.
    // This should be removed once all services stop throwing ResponseStatusException.
    @ExceptionHandler(ResponseStatusException.class)
    public ApiErrorResponse handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        return mapToApiErrorResponse(ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ApiErrorResponse fallbackExceptionHandler(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(INTERNAL_SERVER_ERROR, ex.getMessage(), request);
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
