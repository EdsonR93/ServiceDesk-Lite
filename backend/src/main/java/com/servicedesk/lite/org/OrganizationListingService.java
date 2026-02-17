package com.servicedesk.lite.org;

import com.servicedesk.lite.membership.MembershipRepository;
import com.servicedesk.lite.org.dto.MembershipSummaryResponse;
import com.servicedesk.lite.org.dto.OrganizationSummaryResponse;
import com.servicedesk.lite.org.exception.OrgForbiddenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrganizationListingService {
    private final OrganizationRepository organizationRepository;
    private final MembershipRepository membershipRepository;

    public OrganizationListingService(OrganizationRepository organizationRepository, MembershipRepository membershipRepository) {
        this.organizationRepository = organizationRepository;
        this.membershipRepository = membershipRepository;
    }

    @Transactional(readOnly = true)
    public List<OrganizationSummaryResponse> listMyOrganizations(UUID userId) {
        return organizationRepository.findOrganizationsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<MembershipSummaryResponse> listOrgMembers(UUID orgId, UUID userId) {
        membershipRepository.findByUserIdAndOrgId(userId, orgId).orElseThrow((() -> new OrgForbiddenException("Not an org member")));
        return membershipRepository.findMembersByOrgId(orgId);
    }
}
