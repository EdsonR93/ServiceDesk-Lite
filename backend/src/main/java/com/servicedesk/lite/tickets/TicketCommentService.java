package com.servicedesk.lite.tickets;

import com.servicedesk.lite.auth.AuthContext;
import com.servicedesk.lite.org.context.OrgContext;
import com.servicedesk.lite.tickets.dto.CreateCommentRequest;
import com.servicedesk.lite.tickets.dto.TicketCommentResponse;
import com.servicedesk.lite.user.User;
import com.servicedesk.lite.user.UserValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class TicketCommentService {
    private final TicketRepository ticketRepository;
    private final TicketCommentRepository ticketCommentRepository;
    private final UserValidator userValidator;

    public TicketCommentService(TicketRepository ticketRepository, TicketCommentRepository ticketCommentRepository, UserValidator userValidator) {
        this.ticketRepository = ticketRepository;
        this.ticketCommentRepository = ticketCommentRepository;
        this.userValidator = userValidator;
    }

    @Transactional
    public UUID addComment(UUID ticketId, CreateCommentRequest req) {
        UUID orgId = OrgContext.getOrgId();
        UUID authorId = AuthContext.getUserId();

        Ticket ticket = ticketRepository.findByIdAndOrg_Id(ticketId, orgId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        User author = userValidator.requireActiveCreator(authorId);

        TicketComment ticketComment = new TicketComment();
        ticketComment.setTicket(ticket);
        ticketComment.setAuthor(author);
        ticketComment.setOrg(ticket.getOrg());
        ticketComment.setBody(req.getBody());
        TicketComment saved = ticketCommentRepository.save(ticketComment);

        return saved.getId();
    }

    @Transactional(readOnly = true)
    public Page<TicketCommentResponse> listComments(UUID ticketId, Pageable pageable) {
        UUID orgId = OrgContext.getOrgId();
        ticketRepository.findByIdAndOrg_Id(ticketId, orgId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        Page<TicketComment> comments = ticketCommentRepository
            .findAllByOrg_IdAndTicket_IdOrderByCreatedAtAsc(orgId, ticketId, pageable);

        return comments.map(this::toResponse);
    }

    private TicketCommentResponse toResponse(TicketComment comment) {
        TicketCommentResponse dto = new TicketCommentResponse();
        dto.setId(comment.getId());
        dto.setAuthorUserId(comment.getAuthor().getId());
        dto.setBody(comment.getBody());
        dto.setCreatedAt(comment.getCreatedAt());

        return dto;
    }
}
