package com.servicedesk.lite.org;

import com.servicedesk.lite.org.dto.AddMemberRequest;
import com.servicedesk.lite.org.dto.AddMemberResponse;
import com.servicedesk.lite.org.dto.MembershipSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizations/{orgId}/memberships")
public class MembershipController {
    private final MembershipService membershipService;
    private final OrganizationListingService organizationListingService;

    public MembershipController(MembershipService membershipService, OrganizationListingService organizationListingService) {
        this.membershipService = membershipService;
        this.organizationListingService = organizationListingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddMemberResponse createMembership(@PathVariable UUID orgId, @Valid @RequestBody AddMemberRequest req, @AuthenticationPrincipal Jwt jwt) {
        UUID callerId = UUID.fromString(jwt.getSubject());
        UUID membershipId = membershipService.addMember(orgId, callerId, req);
        return new AddMemberResponse(membershipId);
    }

    @GetMapping
    public List<MembershipSummaryResponse> listOrgMembers(@PathVariable UUID orgId, @AuthenticationPrincipal Jwt jwt) {
        UUID callerId = UUID.fromString(jwt.getSubject());
        return organizationListingService.listOrgMembers(orgId, callerId);
    }
}
