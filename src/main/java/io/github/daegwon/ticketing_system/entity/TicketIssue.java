package io.github.daegwon.ticketing_system.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ticket_issues")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long ticketId;

    public TicketIssue(Long userId, Long ticketId) {
        this.userId = userId;
        this.ticketId = ticketId;
    }
}
