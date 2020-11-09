package com.nickz.traderstats.repository;

import java.time.Duration;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanIteration;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.TraderToken;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

@Repository
public class TokenRepositoryImpl implements TokenRepository {
    
    /*
     * key - token
     * value - traderId
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

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
