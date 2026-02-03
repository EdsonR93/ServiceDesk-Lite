package com.servicedesk.lite.membership;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {

    boolean existsByOrgIdAndUserId(UUID orgId, UUID userId);
    Optional<Membership> findByUserIdAndOrgId(UUID userId, UUID orgId);
}
