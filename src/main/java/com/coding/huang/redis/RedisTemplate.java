package com.coding.huang.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2019/10/22.
 */
@Configuration
@ComponentScan
public class RedisTemplate {

    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getMaxActive());
        poolConfig.setMinIdle(redisConfig.getMinIdle());
        poolConfig.setMaxWaitMillis(redisConfig.getMaxWait());
        JedisPool jp = new JedisPool(poolConfig,redisConfig.getHost(),
                redisConfig.getPort(),redisConfig.getTimeout(),redisConfig.getPassword(),redisConfig.getDatabase());
        return jp;

    }
}
