package io.github.daegwon.ticketing_system.service;

import io.github.daegwon.ticketing_system.entity.Ticket;
import io.github.daegwon.ticketing_system.entity.TicketIssue;
import io.github.daegwon.ticketing_system.repository.TicketIssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketIssueServiceV2_2 {

    private final TicketService ticketService;
    private final TicketIssueRepository ticketIssueRepository;

    @Transactional
    public void issueTicket(Long ticketId, Long userId) {
        Ticket ticket = ticketService.findById(ticketId);
        ticket.issue();
        ticketIssueRepository.save(new TicketIssue(ticketId, userId));
    }
}