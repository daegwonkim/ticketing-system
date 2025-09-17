package io.github.daegwon.ticketing_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Transactional,synchronized 분리를 위해 클래스 추가
 */
@Service
@RequiredArgsConstructor
public class TicketIssueServiceV2_1 {

    private final TicketIssueServiceV2_2 ticketIssueServiceV22;

    public synchronized void issueTicket(Long ticketId, Long userId) {
        ticketIssueServiceV22.issueTicket(ticketId, userId);
    }
}
