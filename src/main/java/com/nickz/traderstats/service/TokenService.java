package com.nickz.traderstats.service;

import com.nickz.traderstats.model.TraderToken;

public interface TokenService {
    String generateToken();

    TraderToken save(TraderToken token);

    String getTraderId(String token);

    boolean delete(String token);
}
