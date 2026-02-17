package com.servicedesk.lite.org;

import com.servicedesk.lite.org.dto.OrganizationSummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    @Query("""
            SELECT new com.servicedesk.lite.org.dto.OrganizationSummaryResponse(
                o.id,
                o.name,
                o.slug,
                m.role
            )
            FROM Organization o
            JOIN Membership m ON m.orgId = o.id
            WHERE m.userId = :userId
        """)
    List<OrganizationSummaryResponse> findOrganizationsByUserId(UUID userId);

    boolean existsByNameIgnoreCase(String name);

    boolean existsBySlug(String slug);
}
