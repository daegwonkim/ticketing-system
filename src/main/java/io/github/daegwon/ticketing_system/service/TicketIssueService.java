package io.github.daegwon.ticketing_system.service;

import org.springframework.stereotype.Service;

@Service
public interface TicketIssueService {
    void issueTicket(Long ticketId, Long userId);
}
