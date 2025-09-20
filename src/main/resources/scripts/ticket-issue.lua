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