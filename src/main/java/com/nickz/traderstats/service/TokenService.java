package com.nickz.traderstats.service;

import com.nickz.traderstats.model.TraderToken;

public interface TokenService {
    String save(int traderId); /* returns generated token */

    String getTraderId(String token);

    boolean delete(String token);
}
