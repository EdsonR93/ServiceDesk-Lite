package com.servicedesk.lite.membership;

import com.servicedesk.lite.org.dto.MembershipSummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {

    @Query("""
            SELECT new com.servicedesk.lite.org.dto.MembershipSummaryResponse(
                u.id,
                u.email,
                m.role
            )
            FROM Membership m
            JOIN User u ON u.id = m.userId
            WHERE m.orgId = :orgId
        """)
    List<MembershipSummaryResponse> findMembersByOrgId(UUID orgId);

    boolean existsByOrgIdAndUserId(UUID orgId, UUID userId);

    Optional<Membership> findByUserIdAndOrgId(UUID userId, UUID orgId);
}
