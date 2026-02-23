package com.servicedesk.lite.tickets;

import org.springframework.stereotype.Service;

@Service
public class TicketsService {
    private final TicketRepository ticketRepository;
    private final TicketKeyGenerator ticketKeyGenerator;

    public TicketsService(TicketRepository ticketRepository, TicketKeyGenerator ticketKeyGenerator) {
        this.ticketRepository = ticketRepository;
        this.ticketKeyGenerator = ticketKeyGenerator;
    }
    
}
