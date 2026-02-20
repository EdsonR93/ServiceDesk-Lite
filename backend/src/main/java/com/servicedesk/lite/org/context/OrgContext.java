package com.servicedesk.lite.org.context;

import java.util.UUID;

public final class OrgContext {
    private static final ThreadLocal<UUID> ORG_ID = new ThreadLocal<>();

    private OrgContext() {
    }

    public static UUID getOrgId() {
        return ORG_ID.get();
    }

    public static void setOrgId(UUID orgId) {
        ORG_ID.set(orgId);
    }

    public static void clear() {
        ORG_ID.remove();
    }
}
