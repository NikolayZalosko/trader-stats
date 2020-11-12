package com.nickz.traderstats.service;

import java.time.Duration;

import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.TraderToken;

public interface TokenService {
    
    String save(int traderId); /* returns generated token */
    
    TraderToken save(TraderToken token, Duration duration); /* for testing purposes */

    String getTraderId(String token) throws ResourceNotFoundException;

    boolean delete(String token) throws ResourceNotFoundException;
}
