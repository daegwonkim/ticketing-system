package io.github.daegwon.ticketing_system.controller;

import io.github.daegwon.ticketing_system.dto.TicketIssueRequestDto;
import io.github.daegwon.ticketing_system.service.TicketIssueServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TicketIssueControllerV1 {

    private final TicketIssueServiceV1 ticketIssueServiceV1;

    @PostMapping("/ticket")
    public void issueTicket(
        @RequestBody TicketIssueRequestDto request
    ) {
        ticketIssueServiceV1.issueTicket(request.ticketId(), request.userId());
    }
}
