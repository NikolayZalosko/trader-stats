package com.nickz.traderstats.repository;

import java.time.Duration;

import com.nickz.traderstats.model.TraderToken;

public interface TokenRepository {
    TraderToken save(TraderToken token, Duration duration);
    String getTraderId(String token);
    boolean delete(String key);
}
