package io.github.daegwon.ticketing_system.repository;

import io.github.daegwon.ticketing_system.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
