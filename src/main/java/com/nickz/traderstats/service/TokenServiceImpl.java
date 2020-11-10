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

    private String generateToken() {
	return UUID.randomUUID().toString();
    }

    @Override
    public String save(int traderId) {
	TraderToken tokenObj = new TraderToken(traderId, this.generateToken());
	return tokenRepository.save(tokenObj, Duration.ofHours(24)).getToken();
    }

    public String getTraderId(String token) {
	return tokenRepository.getTraderId(token);
    }

    @Override
    public boolean delete(String token) {
	return tokenRepository.delete(token);
    }

}
