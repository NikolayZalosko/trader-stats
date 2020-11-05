package com.nickz.traderstats.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nickz.traderstats.model.TraderToken;
import com.nickz.traderstats.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {
    
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public String generateToken() {
	return UUID.randomUUID().toString();
    }

    @Override
    public TraderToken save(TraderToken token) {
	return tokenRepository.save(token, Duration.ofHours(24));
    }


    @Override
    public boolean delete(TraderToken token) {
	return tokenRepository.delete("trader:" + token.getTraderId());
    }

}
