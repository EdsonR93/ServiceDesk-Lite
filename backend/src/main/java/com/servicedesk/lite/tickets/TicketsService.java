package com.servicedesk.lite.tickets;

import com.servicedesk.lite.auth.AuthContext;
import com.servicedesk.lite.membership.MembershipRepository;
import com.servicedesk.lite.org.Organization;
import com.servicedesk.lite.org.OrganizationRepository;
import com.servicedesk.lite.org.context.OrgContext;
import com.servicedesk.lite.tickets.dto.CreateTicketRequest;
import com.servicedesk.lite.user.Status;
import com.servicedesk.lite.user.User;
import com.servicedesk.lite.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class TicketsService {
    private final TicketRepository ticketRepository;
    private final TicketKeyGenerator ticketKeyGenerator;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public TicketsService(TicketRepository ticketRepository, TicketKeyGenerator ticketKeyGenerator, OrganizationRepository organizationRepository, UserRepository userRepository, MembershipRepository membershipRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketKeyGenerator = ticketKeyGenerator;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    public UUID createTicket(CreateTicketRequest req) {
        UUID orgId = OrgContext.getOrgId();
        UUID creatorId = AuthContext.getUserId();

        Organization org = requireOrg(orgId);
        User creator = requireActiveCreator(creatorId);

        Ticket ticket = new Ticket();
        ticket.setOrg(org);
        ticket.setCreatedBy(creator);
        ticket.setTitle(req.getTitle());
        ticket.setDescription(req.getDescription());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setTicketKey(ticketKeyGenerator.nextKey());

        if (req.getAssigneeUserId() != null) {
            User assignee = requireAssignableUser(orgId, req.getAssigneeUserId());
            ticket.setAssignee(assignee);
        }

        return ticketRepository.save(ticket).getId();
    }

    private Organization requireOrg(UUID orgId) {
        Optional<Organization> orgOpt = organizationRepository.findById(orgId);
        return orgOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organization not found"));
    }

    private User requireActiveCreator(UUID userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        User user = userOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

        return user;
    }

    private User requireAssignableUser(UUID orgId, UUID assigneeUserId) {
        if (!membershipRepository.existsByOrgIdAndUserId(orgId, assigneeUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed to assign outside organization");
        }
        Optional<User> userOpt = userRepository.findById(assigneeUserId);

        User user = userOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignee must be ACTIVE");
        }

        return user;
    }
}
