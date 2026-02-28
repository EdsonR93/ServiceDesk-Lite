package com.servicedesk.lite.tickets;

import com.servicedesk.lite.tickets.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final TicketCommentService ticketCommentService;

    public TicketController(TicketService ticketService, TicketCommentService ticketCommentService) {
        this.ticketService = ticketService;
        this.ticketCommentService = ticketCommentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTicketResponse createTicket(@Valid @RequestBody CreateTicketRequest req) {
        UUID id = ticketService.createTicket(req);
        return new CreateTicketResponse(id);
    }

    @PutMapping("/{ticketId}")
    public TicketResponse updateTicket(@PathVariable UUID ticketId, @Valid @RequestBody UpdateTicketRequest req) {
        return ticketService.updateTicket(ticketId, req);
    }

    @PostMapping("/{ticketId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCommentResponse createComment(@PathVariable UUID ticketId, @Valid @RequestBody CreateCommentRequest req) {
        return new CreateCommentResponse(ticketCommentService.addComment(ticketId, req));
    }

    @GetMapping("/{ticketId}/comments")
    public Page<TicketCommentResponse> listComments(@PathVariable UUID ticketId, Pageable pageable) {
        return ticketCommentService.listComments(ticketId, pageable);
    }
}
