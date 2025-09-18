package io.github.daegwon.ticketing_system.aop;

import io.github.daegwon.ticketing_system.annotation.DistributedLock;
import io.github.daegwon.ticketing_system.parser.SpELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 분산 락을 위한 AOP 클래스
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;
    private final SpELParser spELParser;

    /**
     * 분산 락으로 메서드를 감싸는 Around 어드바이스
     *
     * @param joinPoint 호출된 메서드
     * @param distributedLock 분산락 어노테이션
     * @return 비즈니스 로직 실행 결과
     */
    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String key = spELParser.createDynamicKey(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
        RLock lock = redissonClient.getLock(key);

        log.info("Lock acquired for {}", key);

        try {
            if (!lock.tryLock(10, 1, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Failed to acquire lock.");
            }

            // 별도 트랜잭션으로 비즈니스 로직 수행
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            throw new InterruptedException("Thread interrupted while waiting for lock.");
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
