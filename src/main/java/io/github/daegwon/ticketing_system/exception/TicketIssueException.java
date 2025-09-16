package io.github.daegwon.ticketing_system.exception;

import io.github.daegwon.ticketing_system.enumerate.ErrorCode;
import lombok.Getter;

@Getter
public class TicketIssueException extends RuntimeException {

    private final ErrorCode errorCode;

    public TicketIssueException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public TicketIssueException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}