package io.github.daegwon.ticketing_system.entity;

import io.github.daegwon.ticketing_system.enumerate.ErrorCode;
import io.github.daegwon.ticketing_system.exception.TicketIssueException;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer total_quantity;

    @Column(nullable = false)
    private Integer issued_quantity;

    public void issue() {
        if (total_quantity <= issued_quantity) {
            throw new TicketIssueException(ErrorCode.INVALID_TICKET_ISSUE_QUANTITY);
        }

        issued_quantity++;
    }
}
