package io.github.daegwon.ticketing_system.service.ticket_issue;


import io.github.daegwon.ticketing_system.entity.Ticket;
import io.github.daegwon.ticketing_system.entity.TicketIssue;
import io.github.daegwon.ticketing_system.repository.TicketIssueRepository;
import io.github.daegwon.ticketing_system.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketIssueServiceV3 {

    private final TicketService ticketService;
    private final TicketIssueRepository ticketIssueRepository;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500))
    @Transactional
    public void issueTicket(Long ticketId, Long userId) {
        Ticket ticket = ticketService.findById(ticketId);
        ticket.issue();
        ticketIssueRepository.save(new TicketIssue(ticketId, userId));
    }
}
