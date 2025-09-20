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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketIssueServiceV6 {

    private final TicketService ticketService;
    private final TicketIssueRepository ticketIssueRepository;

    private final StringRedisTemplate stringRedisTemplate;
    private final ResourceLoader resourceLoader;
    private RedisScript<Long> redisScript;

    @Value("${ticket.total-quantity}")
    private int ticketTotalQuantity;

    @PostConstruct
    public void init() {
        try {
            Resource resource = resourceLoader.getResource("classpath:scripts/ticket-issue.lua");
            String scriptContent = FileCopyUtils.copyToString(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
            );
            this.redisScript = RedisScript.of(scriptContent, Long.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Redis Lua script", e);
        }
    }

    public void issueTicket(Long ticketId, Long userId) {
        String redisKey = String.format("ticket:%s", ticketId);
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
