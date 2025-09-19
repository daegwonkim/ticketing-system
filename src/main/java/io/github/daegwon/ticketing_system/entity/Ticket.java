package io.github.daegwon.ticketing_system.entity;

import io.github.daegwon.ticketing_system.enumerate.ErrorCode;
import io.github.daegwon.ticketing_system.exception.TicketIssueException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tickets")
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Setter
    @Column(name = "issued_quantity", nullable = false)
    private Integer issuedQuantity;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

//    @Version
//    private Long version = 0L;

    public void issue() {
        if (totalQuantity <= issuedQuantity) {
            throw new TicketIssueException(ErrorCode.INVALID_TICKET_ISSUE_QUANTITY);
        }

        issuedQuantity++;
    }
}
