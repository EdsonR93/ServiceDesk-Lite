package com.servicedesk.lite.tickets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Optional<Ticket> findByIdAndOrg_Id(UUID ticketId, UUID orgId);

    Page<Ticket> findAllByOrg_Id(UUID orgId, Pageable pageable);

    Page<Ticket> findAllByOrg_IdAndStatus(UUID orgId, TicketStatus status, Pageable pageable);
}
