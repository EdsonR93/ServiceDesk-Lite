package com.servicedesk.lite.membership;

import java.util.Arrays;
import java.util.Optional;

public enum MembershipRole {
    ADMIN, AGENT, REQUESTER;

    public static Optional<MembershipRole> from(String value) {
        return Arrays.stream(values())
            .filter(s -> s.name().equalsIgnoreCase(value))
            .findFirst();
    }
}
