package io.github.daegwon.ticketing_system.enumerate;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_TICKET_ISSUE_QUANTITY("T001", "발급 가능한 수량을 초과했습니다.")
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
