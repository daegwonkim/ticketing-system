package io.github.daegwon.ticketing_system.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "ticket_issues")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public TicketIssue(Long userId, Long ticketId) {
        this.userId = userId;
        this.ticketId = ticketId;
    }
}
