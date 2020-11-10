package com.nickz.traderstats.controller.v1;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.TraderStatus;
import com.nickz.traderstats.model.TraderToken;
import com.nickz.traderstats.service.EmailService;
import com.nickz.traderstats.service.TokenService;
import com.nickz.traderstats.service.TraderService;

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {
    private TokenService tokenService;
    private TraderService traderService;
    private Logger logger = LoggerFactory.getLogger(TraderController.class);

    public AuthController(TokenService tokenService, TraderService traderService) {
	this.tokenService = tokenService;
	this.traderService = traderService;
    }

    @GetMapping("/confirm_email/{token}")
    public Trader confirmEmail(@PathVariable String token) {
	int traderId = Integer.valueOf(tokenService.getTraderId(token));
	Trader traderToUpdate = traderService.getOne(traderId);
	traderToUpdate.setStatus(TraderStatus.NOT_APPROVED_YET);
	tokenService.delete(token);
	
	return traderService.update(traderToUpdate);
    }
}
