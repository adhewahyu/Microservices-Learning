package com.dan.shared.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@Slf4j
@Configuration
@ConfigurationProperties(prefix = "redis")
@Setter
@Getter
public class RedisConfiguration {

    private String host;
    private String password;
    private Integer port;
    private Integer dbIndex;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxTotal;
    private Long timeout;
    private Long maxWait;
    private Long shutdown;

    @Bean(name="redisClientFactory")
    public LettuceConnectionFactory getRedisClientFactory(){
        log.info("redisClientFactory is created...");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(this.getHost());
        redisStandaloneConfiguration.setPort(this.getPort());
        redisStandaloneConfiguration.setPassword(this.getPassword());
        redisStandaloneConfiguration.setDatabase(ObjectUtils.isEmpty(this.dbIndex) ? 0 : this.dbIndex);
        var poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(ObjectUtils.isEmpty(this.maxIdle) ? 10 : this.maxIdle);
        poolConfig.setMinIdle(ObjectUtils.isEmpty(this.minIdle) ? 1 : this.minIdle);
        poolConfig.setMaxTotal(ObjectUtils.isEmpty(this.maxTotal) ? 10 : this.maxTotal);
        poolConfig.setMaxWait(ObjectUtils.isEmpty(this.maxWait) ? Duration.ofMillis(5000) : Duration.ofMillis(this.maxWait));
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
            .commandTimeout(ObjectUtils.isEmpty(this.timeout) ? Duration.ofSeconds(10) : Duration.ofSeconds(this.timeout))
            .shutdownTimeout(ObjectUtils.isEmpty(this.shutdown) ? Duration.ZERO : Duration.ofSeconds(this.shutdown))
            .poolConfig(poolConfig)
            .build();

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
        lettuceConnectionFactory.setShareNativeConnection(false);
        return lettuceConnectionFactory;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        log.info("redisTemplate is created...");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(this.getRedisClientFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setEnableDefaultSerializer(true);
        return redisTemplate;
    }

}
