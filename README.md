# 자바에서 동시성 문제를 해결하는 방법

## 👋 프로젝트 개요
멀티 스레드 환경에서 공유 자원에 대한 동시 접근으로 인해 발생하는 Race Condition, 데이터 무결성 등의 동시성 이슈를 해결하는 방법을 구현하고 비교합니다.

## 🎯 주요 목표
- 각 동기화 방법의 정확성 검증
- 성능 비교

## 📚 기술 스택
- **Language**: Java 17+
- **Framework**: Spring Boot 3.x
- **Database**: MySQL 8.0, Redis 7.x
- **Test**: JMeter

## 📁 프로젝트 구조
```
src/
└── main/
   ├── java/
   │   └── io.github.daegwon.ticketing_system/
   │       ├── annotation/      # 분산락 구현용 어노테이션
   │       ├── aop/             # 분산락 구현용 AOP
   │       ├── parser/          # 분산락 구현용 SpEL 파서
   │       ├── config/          # Redis 설정
   │       ├── exception/       # 커스텀 예외 클래스
   │       ├── enumerate/       # 에러 코드
   │       ├── service/         # 각 버전별 동기화 구현
   │       ├── entity/          # JPA 엔티티
   │       ├── repository/      # 데이터 접근 계층
   │       ├── dto/             # DTO
   │       └── controller/      # 각 버전별 REST API 엔드포인트
   └── resources/
       ├── scripts/             # Lua 스크립트 파일
       └── application.yml      # 애플리케이션 설정
```

## 🚀 동기화 방법
- V1: 동기화 처리를 하지 않은 버전
- V2: `synchronized` 키워드를 사용하여 동기화 처리한 버전
- V3: 낙관적 락을 사용하여 동기화 처리한 버전
- V4: 비관적 락을 사용하여 동기화 처리한 버전
- V5: Redis 분산락을 사용하여 동기화 처리한 버전
- V6: Redis + Lua를 사용하여 동기화 처리한 버전

## 📊 테스트 시나리오
#### Thread Properties
- Number of Threads(users): 500
- Ramp-up period(seconds): 10
- Loop Count: 10

#### 성능 측정 지표
- 응답시간(Average, Min, Max)
- 에러율(Error %)
- 단위 시간당 처리량(Throughput)

## 📈 성능 비교 결과
| 동기화 방법 | TPS | 응답시간(Avg) | 응답시간(Max) | 에러율 |
|----------|-----|-------------|-------------|-------|
| Synchronized | 187.0 | 1.556ms | 3.399ms | 0.00% |
| Optimistic Lock | 196.7 | 0.760ms | 5.390ms | 0.64% |
| Pessimistic Lock | 278.7 | 0.681ms | 1.872ms | 0.00% |
| Redis Lock | 277.0 | 0.763ms | 2.716ms | 0.00% |
| Redis + Lua | 500.6 | 0.001ms | 0.052ms | 0.00% |

## ⭐️ 결론
#### Synchronized
- 구현이 단순하며 별도의 의존성이 불필요함
- 멀티스레드 환경에서 블로킹으로 인한 성능 저하가 가장 심함
- 특히 실행 시간이 짧은 작업에 대한 테스트이다 보니 락 획득/해제 오버헤드가 상대적으로 크게 측정된 것으로 보임
- 분산 환경에서 사용 불가

#### Optimistic Lock
- 실제로 락을 거는 것은 아니기 때문에 자원을 효율적으로 사용할 수 있으며, 데드락 가능성이 없음
- 단, 동시성 충돌 시 재시도 로직이 필요하며, 버전 충돌 에러가 발생할 수 있음
- 충돌이 적은 환경에서는 효율적일 수 있으나, 충돌이 많은 환경에서는 재시도가 급증하여 성능이 저하됨

#### Pessimistic Lock
- 충돌 가능성이 높은 경우 낙관적 락에 비해 안정적인 성능을 보임
- 단, 락이 필요 없는 경우에도 적용되어 성능에 영향을 줄 수 있음
- 또한 트랜잭션이 길거나 락이 걸리는 데이터가 많을수록 데드락의 가능성이 높아짐
- DB 의존적

#### Redis Distributed Lock
- 분산 환경 지원
- 준수한 성능과 높은 안정성
- 락 획득/해제 로직을 직접 구현해주어야 함
- Redis 의존적

#### Redis + Lua
- 분산 환경 지원
- 압도적인 성능과 높은 안정성
- 원자적 연산으로 네트워크 오버헤드 최소화
- Lua 스크립트 작성 및 관리 필요
- Redis 의존적
