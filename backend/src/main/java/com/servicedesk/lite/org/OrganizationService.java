package com.servicedesk.lite.org;

import com.servicedesk.lite.membership.Membership;
import com.servicedesk.lite.membership.MembershipRepository;
import com.servicedesk.lite.membership.MembershipRole;
import com.servicedesk.lite.org.dto.CreateOrganizationRequest;
import com.servicedesk.lite.org.exception.OrganizationSlugAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final MembershipRepository membershipRepository;


    public OrganizationService(OrganizationRepository organizationRepository, MembershipRepository membershipRepository) {
        this.organizationRepository = organizationRepository;
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    public UUID createOrganization(CreateOrganizationRequest req, UUID creatorUserId) {

        boolean slugExists = organizationRepository.existsBySlug(req.getSlug());
        if (slugExists) {
            throw new OrganizationSlugAlreadyExistsException("Organization slug already exists: " + req.getSlug());
        }
        Organization savedOrg = organizationRepository.save(new Organization(req.getName(), req.getSlug()));
        UUID orgId = savedOrg.getId();
        membershipRepository.save(new Membership(orgId, creatorUserId, MembershipRole.ADMIN));
        return orgId;
    }
}
