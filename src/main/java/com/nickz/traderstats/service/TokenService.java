package com.nickz.traderstats.service;

import java.time.Duration;

import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.UserToken;

public interface TokenService {
    
    String save(int userId); /* returns generated token */
    
    UserToken save(UserToken token, Duration duration); /* for testing purposes */

    String getUserId(String token) throws ResourceNotFoundException;

    boolean delete(String token) throws ResourceNotFoundException;
}
