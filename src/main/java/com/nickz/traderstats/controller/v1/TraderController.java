package com.nickz.traderstats.controller.v1;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.exception.TraderAlreadyExistsException;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.TraderToken;
import com.nickz.traderstats.service.EmailService;
import com.nickz.traderstats.service.TokenService;
import com.nickz.traderstats.service.TraderService;

@RestController
@RequestMapping("/api/v1/traders")
class TraderController {
    private TraderService traderService;
    private TokenService tokenService;
    private EmailService emailService;
    private Logger logger = LoggerFactory.getLogger(TraderController.class);

    public TraderController(TraderService traderService, TokenService tokenService, EmailService emailService) {
	this.traderService = traderService;
	this.tokenService = tokenService;
	this.emailService = emailService;
    }

    @GetMapping
    public List<Trader> getAllTraders() {
	return traderService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trader create(@RequestBody @Valid TraderRegistrationDto traderDto) throws TraderAlreadyExistsException {
	Trader savedTrader = traderService.save(traderDto);
	String token = tokenService.save(savedTrader.getId());
	try {
	    emailService.sendVerificationEmail(traderDto, token);
	} catch (MailException e) {
	    logger.debug(e.getMessage());
	} catch (MessagingException e) {
	    logger.debug(e.getMessage());
	}
	
	return savedTrader;
    }

}
