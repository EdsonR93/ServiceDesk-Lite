package com.servicedesk.lite.tickets;

import com.servicedesk.lite.tickets.dto.CreateTicketRequest;
import com.servicedesk.lite.tickets.dto.CreateTicketResponse;
import com.servicedesk.lite.tickets.dto.TicketResponse;
import com.servicedesk.lite.tickets.dto.UpdateTicketRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
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
}
