package io.github.daegwon.ticketing_system.dto;

public record TicketIssueRequestDto(
        Long ticketId,
        Long userId
) {
}
