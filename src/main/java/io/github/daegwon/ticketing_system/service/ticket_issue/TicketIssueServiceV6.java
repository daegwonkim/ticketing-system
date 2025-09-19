package io.github.daegwon.ticketing_system.service.ticket_issue;


import io.github.daegwon.ticketing_system.entity.Ticket;
import io.github.daegwon.ticketing_system.entity.TicketIssue;
import io.github.daegwon.ticketing_system.enumerate.ErrorCode;
import io.github.daegwon.ticketing_system.exception.TicketIssueException;
import io.github.daegwon.ticketing_system.repository.TicketIssueRepository;
import io.github.daegwon.ticketing_system.service.ticket.TicketService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketIssueServiceV6 {

    private final TicketService ticketService;
    private final TicketIssueRepository ticketIssueRepository;

    private final StringRedisTemplate stringRedisTemplate;
    private RedisScript<Long> redisScript;

    @Value("${ticket.total-quantity}")
    private int ticketTotalQuantity;

    @PostConstruct
    public void init() {
        String TICKET_ISSUE_SCRIPT = """
                -- KEYS[1]: 현재까지 발급된 티켓 수를 저장하는 키 (예: "ticket:1")
                -- ARGV[1]: 최대 발급 가능 티켓 수
                
                local max_tickets = tonumber(ARGV[1])
                
                -- 현재까지 발급된 티켓 수 조회
                local current_count = redis.call('GET', KEYS[1]) or 0
                current_count = tonumber(current_count)
                
                -- 최대 발급 수량 체크
                if current_count >= max_tickets then
                    return 0
                end
                
                -- 티켓 발급: 카운트 증가
                local new_count = redis.call('INCR', KEYS[1])
                return new_count
                """;

        this.redisScript = RedisScript.of(TICKET_ISSUE_SCRIPT, Long.class);
    }

    public void issueTicket(Long ticketId, Long userId) {
        String redisKey = String.format("ticket:%s'", ticketId);
        List<String> keys = Collections.singletonList(redisKey);

        Long result = stringRedisTemplate.execute(redisScript, keys, String.valueOf(ticketTotalQuantity));

        if (result == 0) {
            throw new TicketIssueException(ErrorCode.INVALID_TICKET_ISSUE_QUANTITY);
        } else {
            Ticket ticket = ticketService.findById(ticketId);
            ticket.setIssuedQuantity(result.intValue());
            ticketIssueRepository.save(new TicketIssue(userId, ticketId));
        }
    }
}
