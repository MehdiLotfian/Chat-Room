package com.talkademy.phase08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisProvider {

    private static final Jedis jedis = new Jedis();

    @Bean
    public Jedis getJedis() {
        return jedis;
    }

}
