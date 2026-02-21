package com.servicedesk.lite.tickets;

import java.util.Arrays;
import java.util.Optional;

public enum TicketStatus {
    OPEN, IN_PROGRESS, RESOLVED, CLOSED;

    public static Optional<TicketStatus> from(String value) {
        return Arrays.stream(values())
            .filter(s -> s.name().equalsIgnoreCase(value))
            .findFirst();
    }
}
