package com.servicedesk.lite.tickets;

import com.servicedesk.lite.auth.AuthContext;
import com.servicedesk.lite.org.Organization;
import com.servicedesk.lite.org.OrganizationRepository;
import com.servicedesk.lite.org.context.OrgContext;
import com.servicedesk.lite.tickets.dto.CreateTicketRequest;
import com.servicedesk.lite.tickets.dto.TicketResponse;
import com.servicedesk.lite.tickets.dto.UpdateTicketRequest;
import com.servicedesk.lite.user.User;
import com.servicedesk.lite.user.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static com.servicedesk.lite.tickets.TicketStatus.*;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketKeyGenerator ticketKeyGenerator;
    private final OrganizationRepository organizationRepository;
    private final UserValidator userValidator;


    public TicketService(TicketRepository ticketRepository, TicketKeyGenerator ticketKeyGenerator, OrganizationRepository organizationRepository, UserValidator userValidator) {
        this.ticketRepository = ticketRepository;
        this.ticketKeyGenerator = ticketKeyGenerator;
        this.organizationRepository = organizationRepository;
        this.userValidator = userValidator;
    }

    @Transactional
    public UUID createTicket(CreateTicketRequest req) {
        UUID orgId = OrgContext.getOrgId();
        UUID creatorId = AuthContext.getUserId();

        Organization org = requireOrg(orgId);
        User creator = userValidator.requireActiveCreator(creatorId);

        Ticket ticket = new Ticket();
        ticket.setOrg(org);
        ticket.setCreatedBy(creator);
        ticket.setTitle(req.getTitle());
        ticket.setDescription(req.getDescription());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setTicketKey(ticketKeyGenerator.nextKey());

        if (req.getAssigneeUserId() != null) {
            User assignee = userValidator.requireAssignableUser(orgId, req.getAssigneeUserId());
            ticket.setAssignee(assignee);
        }

        return ticketRepository.save(ticket).getId();
    }

    private Organization requireOrg(UUID orgId) {
        Optional<Organization> orgOpt = organizationRepository.findById(orgId);
        return orgOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organization not found"));
    }

    @Transactional
    public TicketResponse updateTicket(UUID ticketId, UpdateTicketRequest req) {
        UUID orgId = OrgContext.getOrgId();
        Ticket ticket = ticketRepository
            .findByIdAndOrg_Id(ticketId, orgId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        if (req.getTitle() != null) {
            ticket.setTitle(req.getTitle());
        }
        if (req.getDescription() != null) {
            ticket.setDescription(req.getDescription());
        }
        if (Boolean.TRUE.equals(req.getClearAssignee())) {
            if (req.getAssigneeUserId() != null) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot set and clear assignee at the same time"
                );
            }
            ticket.setAssignee(null);
        } else if (req.getAssigneeUserId() != null) {
            User assignee = userValidator.requireAssignableUser(orgId, req.getAssigneeUserId());
            ticket.setAssignee(assignee);
        }
        if (req.getStatus() != null && req.getStatus() != ticket.getStatus()) {
            validateStatusTransition(ticket.getStatus(), req.getStatus());
            ticket.setStatus(req.getStatus());
        }
        Ticket savedTicket = ticketRepository.save(ticket);

        return mapToResponse(savedTicket);

    }

    private TicketResponse mapToResponse(Ticket ticket) {
        TicketResponse res = new TicketResponse();
        res.setId(ticket.getId());
        res.setTicketKey(ticket.getTicketKey());
        res.setStatus(ticket.getStatus());
        res.setOrgId(ticket.getOrg().getId());
        res.setCreatedByUserId(ticket.getCreatedBy().getId());
        res.setAssigneeUserId(
            ticket.getAssignee() != null ? ticket.getAssignee().getId() : null
        );
        res.setCreatedAt(ticket.getCreatedAt());
        res.setUpdatedAt(ticket.getUpdatedAt());
        return res;
    }

    private void validateStatusTransition(TicketStatus from, TicketStatus to) {
        boolean valid = switch (from) {
            case OPEN -> to == IN_PROGRESS || to == RESOLVED || to == CLOSED;
            case IN_PROGRESS -> to == RESOLVED || to == OPEN;
            case RESOLVED -> to == CLOSED || to == IN_PROGRESS;
            case CLOSED -> false;
        };

        if (!valid) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Invalid status transition"
            );
        }
    }
}
