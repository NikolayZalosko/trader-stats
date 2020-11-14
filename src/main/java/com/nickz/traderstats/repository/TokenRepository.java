package com.nickz.traderstats.repository;

import java.time.Duration;
import java.util.Optional;

import com.nickz.traderstats.model.UserToken;

public interface TokenRepository {
    
    UserToken save(UserToken token, Duration duration);
    
    Optional<String> getUserId(String token);
    
    boolean delete(String key);
}
