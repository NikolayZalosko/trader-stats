package com.nickz.traderstats.repository;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.TraderToken;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    /*
     * key - token value - traderId
     */
    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> valueOperations;

    public TokenRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
	this.redisTemplate = redisTemplate;
	this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public TraderToken save(TraderToken token, Duration duration) {
	String key = token.getToken();
	String value = String.valueOf(token.getTraderId());
	valueOperations.set(key, value, duration);
	return token;
    }

    @Override
    public String getTraderId(String token) {
	return valueOperations.get(token);
    }

    @Override
    public boolean delete(String key) {
	return redisTemplate.delete(key);
    }

}
