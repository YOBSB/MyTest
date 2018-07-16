package com.miniworld.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhusq
 */
@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.pool.maxIdle}")
    private int  maxIdle;

    @Value("${redis.pool.minIdle}")
    private int minIdle;

    @Value("${redis.pool.maxWaitMillis}")
    private long maxWaitMillis;

    @Value("${redis.pool.maxActive}")
    private int maxActive;

    @Value("${redis.timeout}")
    private int timeout;


    @Bean(name = "redisPoolConfig")
    public JedisPoolConfig initJedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(@Qualifier("redisPoolConfig") JedisPoolConfig poolConfig) {
        JedisConnectionFactory connFactory = new JedisConnectionFactory();
        connFactory.setPoolConfig(poolConfig);
        connFactory.setHostName(host);
        connFactory.setPort(port);
        connFactory.setPassword(password);
        return connFactory;
    }

    @Bean(name = "jedisPool")
    public JedisPool initJedisPool(@Qualifier("redisPoolConfig") JedisPoolConfig poolConfig) {
        JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout,password);
        return jedisPool;
    }
}
