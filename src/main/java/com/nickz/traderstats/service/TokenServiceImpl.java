package com.nickz.traderstats.service;

import java.time.Duration;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.UserToken;
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
    public String save(int userId) {
	UserToken tokenObj = new UserToken(userId, this.generateToken());
	return tokenRepository.save(tokenObj, Duration.ofHours(24)).getToken();
    }

    public UserToken save(UserToken token, Duration duration) {
	return tokenRepository.save(token, duration);
    }

    public Integer getUserId(String token) throws ResourceNotFoundException {
	return Integer.valueOf(tokenRepository.getUserId(token)
		.orElseThrow(() -> new ResourceNotFoundException("The token is not valid")));
    }

    @Override
    public boolean delete(String token) {
	return tokenRepository.delete(token);
    }
}
