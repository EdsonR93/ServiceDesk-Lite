package com.servicedesk.lite.user;

import java.util.Arrays;
import java.util.Optional;

public enum Status {
    ACTIVE, DISABLED;

    public static Optional<Status> from(String value) {
        return Arrays.stream(values())
            .filter(s -> s.name().equalsIgnoreCase(value))
            .findFirst();
    }
}
