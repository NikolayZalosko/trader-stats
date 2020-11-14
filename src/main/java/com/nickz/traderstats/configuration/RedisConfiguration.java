package com.nickz.traderstats.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
	RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
	return new JedisConnectionFactory(config);
    }

    @Bean
    StringRedisSerializer stringRedisSerializer() {
	return new StringRedisSerializer();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
	RedisTemplate<String, Object> template = new RedisTemplate<>();
	template.setConnectionFactory(jedisConnectionFactory());
	template.setKeySerializer(stringRedisSerializer());
	template.setValueSerializer(stringRedisSerializer());
	return template;
    }

}
