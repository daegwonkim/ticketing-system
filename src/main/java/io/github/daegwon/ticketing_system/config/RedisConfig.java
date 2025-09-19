package io.github.daegwon.ticketing_system.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis 환경 설정
 */
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private Integer port;

    /**
     * Redis 서버와의 연결을 관리하는 ConnectionFactory 생성
     *
     * @return RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    /**
     * RedissonClient 등록
     *
     * @return RedissonClient
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        return Redisson.create(config);
    }

    /**
     * StringRedisTemplate 등록
     * @return StringRedisTemplate
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
