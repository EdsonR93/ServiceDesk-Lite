package com.servicedesk.lite.error;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiErrorResponse(OffsetDateTime timestamp, int statusCode, String errorType, String errorMessage,
                               String path, List<ApiFieldError> fieldErrors) {
}
