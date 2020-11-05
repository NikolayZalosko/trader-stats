package com.nickz.traderstats.repository;

import java.time.Duration;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.TraderToken;

@Repository
public class TokenRepositoryImpl implements TokenRepository {
    
    @Autowired
    private RedisTemplate<String, String> template;
    
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Override
    public TraderToken save(TraderToken token, Duration duration) {
	String key = "trader:" + token.getTraderId();
	String value = token.getToken();
	valueOperations.set(key, value, duration);
	return token;
    }
    
    @Override
    public String find(String key) {
	return valueOperations.get(key);
    }

    @Override
    public boolean delete(String key) {
	return template.delete(key);
    }

}
