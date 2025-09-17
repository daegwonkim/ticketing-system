package io.github.daegwon.ticketing_system.controller;

import io.github.daegwon.ticketing_system.dto.TicketIssueRequestDto;
import io.github.daegwon.ticketing_system.service.ticket_issue.TicketIssueServiceV2_1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class TicketIssueControllerV2 {

    private final TicketIssueServiceV2_1 ticketIssueServiceV2;

    @PostMapping("/ticket")
    public void issueTicket(
        @RequestBody TicketIssueRequestDto request
    ) {
        ticketIssueServiceV2.issueTicket(request.ticketId(), request.userId());
    }
}
