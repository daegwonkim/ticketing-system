package io.github.daegwon.ticketing_system.controller;

import io.github.daegwon.ticketing_system.dto.TicketIssueRequestDto;
import io.github.daegwon.ticketing_system.service.TicketIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TicketIssueController {

    private final TicketIssueService ticketIssueService;

    @PostMapping("/api/ticket")
    public void issueTicket(
        @RequestBody TicketIssueRequestDto request
    ) {
        ticketIssueService.issueTicket(request.ticketId(), request.userId());
    }
}
