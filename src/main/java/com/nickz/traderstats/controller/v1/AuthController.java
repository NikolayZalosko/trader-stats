package com.nickz.traderstats.controller.v1;

import java.util.Optional;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private TraderService traderService;
    
    private Logger logger = LoggerFactory.getLogger(TraderController.class);
    
    @GetMapping("/getToken")
    public String getToken() {
	String token = tokenService.generateToken();
	return token;
    }
    
    @GetMapping("/sendToken")
    public String sendToken() {
	String token = tokenService.generateToken();
	TraderRegistrationDto traderDto = new TraderRegistrationDto();
	traderDto.setFirstName("ludvig");
	traderDto.setLastName("bodmer");
	traderDto.setEmail("wemen60755@gusronk.com");
	traderDto.setPassword("12345678");
	try {    
	    emailService.sendVerificationEmail(traderDto, token);
	} catch (MailException e) {
	    logger.error(e.getMessage());
	} catch (MessagingException e) {
	    logger.error(e.getMessage());
	}
	TraderToken tokenObj = new TraderToken();
	tokenObj.setTraderId(2);
	tokenObj.setToken(token);
	tokenService.save(tokenObj);
	return token;
    }
    
    @GetMapping("/confirm_email/{token}")
    public Trader confirmEmail(@PathVariable String token) {
//	Optional<Integer> traderIdOpt = Optional.of(Integer.valueOf(tokenService.getTraderId(token)));
	
//	String nullableTraderId = 
	int traderId = Integer.valueOf(tokenService.getTraderId(token));
	Trader traderToUpdate = traderService.getOne(traderId);
	traderToUpdate.setStatus(TraderStatus.NOT_APPROVED_YET);
	tokenService.delete(token);
	/*
	Trader oldTrader = traderService.findById(traderId);
	Trader newTrader = new Trader();
	BeanUtils.copyProperties(oldTrader, newTrader);
	newTrader.setStatus(TraderStatus.NOT_APPROVED_YET);
	*/
	
	return traderService.update(traderToUpdate);
    }
}
