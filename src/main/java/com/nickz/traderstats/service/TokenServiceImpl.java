package com.nickz.traderstats.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nickz.traderstats.model.TraderToken;
import com.nickz.traderstats.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {
    private TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
	this.tokenRepository = tokenRepository;
    }

    @Override
    public String generateToken() {
	return UUID.randomUUID().toString();
    }

    @Override
    public TraderToken save(TraderToken token) {
	return tokenRepository.save(token, Duration.ofHours(24));
    }

    public String getTraderId(String token) {
	return tokenRepository.getTraderId(token);
    }

    @Override
    public boolean delete(String token) {
	return tokenRepository.delete(token);
    }

}
