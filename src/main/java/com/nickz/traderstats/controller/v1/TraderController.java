package com.nickz.traderstats.controller.v1;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.TraderToken;
import com.nickz.traderstats.service.EmailService;
import com.nickz.traderstats.service.TokenService;
import com.nickz.traderstats.service.TraderService;


@RestController
@RequestMapping("/api/v1/traders")
class TraderController {
    @Autowired
    private TraderService traderService;
    
    @Autowired
    private TokenService tokenService;
    
    private Logger logger = LoggerFactory.getLogger(TraderController.class);
    
    @Autowired
    private EmailService emailService;
    
    @GetMapping
    public List<Trader> getAllTraders() {
	return traderService.findAll();
    }
    
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Trader create(@RequestBody @Valid TraderRegistrationDto traderDto) {
	String token = tokenService.generateToken();
	try {
	    emailService.sendVerificationEmail(traderDto, token);
	    
	} catch (MailException e) {
	    logger.error(e.getMessage());
	} catch (MessagingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Trader savedTrader = traderService.save(traderDto);
	int traderId = savedTrader.getId();
	tokenService.save(new TraderToken(traderId, token));
	return savedTrader;
    }
    
}
