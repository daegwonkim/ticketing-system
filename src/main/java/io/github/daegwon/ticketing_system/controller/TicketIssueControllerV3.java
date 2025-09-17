package io.github.daegwon.ticketing_system.controller;

import io.github.daegwon.ticketing_system.dto.TicketIssueRequestDto;
import io.github.daegwon.ticketing_system.service.ticket_issue.TicketIssueServiceV3;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3")
@RequiredArgsConstructor
public class TicketIssueControllerV3 {

    private final TicketIssueServiceV3 ticketIssueServiceV3;

    @PostMapping("/ticket")
    public void issueTicket(
        @RequestBody TicketIssueRequestDto request
    ) {
        ticketIssueServiceV3.issueTicket(request.ticketId(), request.userId());
    }
}
