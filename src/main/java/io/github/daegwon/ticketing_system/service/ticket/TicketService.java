package io.github.daegwon.ticketing_system.service.ticket;

import io.github.daegwon.ticketing_system.entity.Ticket;
import io.github.daegwon.ticketing_system.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow();
    }
}
