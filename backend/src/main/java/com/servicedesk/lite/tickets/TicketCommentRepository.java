package com.servicedesk.lite.tickets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketCommentRepository extends JpaRepository<TicketComment, UUID> {
    Page<TicketComment> findAllByOrg_IdAndTicket_IdOrderByCreatedAtAsc(UUID orgId, UUID ticketId, Pageable pageable);
}
