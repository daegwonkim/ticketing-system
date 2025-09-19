package io.github.daegwon.ticketing_system.controller;

import io.github.daegwon.ticketing_system.dto.TicketIssueRequestDto;
import io.github.daegwon.ticketing_system.service.ticket_issue.TicketIssueServiceV6;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v6")
@RequiredArgsConstructor
public class TicketIssueControllerV6 {

    private final TicketIssueServiceV6 ticketIssueServiceV6;

    @PostMapping("/ticket")
    public void issueTicket(
        @RequestBody TicketIssueRequestDto request
    ) {
        ticketIssueServiceV6.issueTicket(request.ticketId(), request.userId());
    }
}
