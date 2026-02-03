package com.servicedesk.lite.org;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsBySlug(String slug);
}
