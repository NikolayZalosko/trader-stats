package com.nickz.traderstats.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.nickz.traderstats.service.RedisKeyExpiredEventListener;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
	RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
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

    /*
    @Bean
    public RedisKeyExpiredEventListener redisKeyExpiredEventListener() {
	return new RedisKeyExpiredEventListener(redisMessageListenerContainer());
    }
    */
    
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
	RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	container.setConnectionFactory(this.jedisConnectionFactory());
	return container;
    }
}
