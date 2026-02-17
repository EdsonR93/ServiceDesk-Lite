package com.servicedesk.lite.org;

import com.servicedesk.lite.org.dto.CreateOrganizationRequest;
import com.servicedesk.lite.org.dto.CreateOrganizationResponse;
import com.servicedesk.lite.org.dto.OrganizationSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationListingService organizationListingService;

    public OrganizationController(OrganizationService organizationService, OrganizationListingService organizationListingService) {
        this.organizationService = organizationService;
        this.organizationListingService = organizationListingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrganizationResponse createOrganization(@Valid @RequestBody CreateOrganizationRequest req, @AuthenticationPrincipal Jwt jwt) {
        UUID creatorId = UUID.fromString(jwt.getSubject());
        UUID orgId = organizationService.createOrganization(req, creatorId);
        return new CreateOrganizationResponse(orgId);
    }

    @GetMapping
    public List<OrganizationSummaryResponse> listMyOrganizations(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return organizationListingService.listMyOrganizations(userId);
    }
}
