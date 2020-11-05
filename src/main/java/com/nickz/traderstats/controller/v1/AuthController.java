package com.nickz.traderstats.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.service.EmailService;
import com.nickz.traderstats.service.TokenService;

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {
    
    @Autowired
    private TokenService tokenService;
    
    private Logger logger = LoggerFactory.getLogger(TraderController.class);
    
    @Autowired
    private EmailService emailService;
    
    @GetMapping("/getToken")
    public String getToken() {
	String token = tokenService.generateToken();
	return token;
    }
    
    @GetMapping("/sendToken")
    public String sendToken() {
	String token = tokenService.generateToken();
	try {
	    TraderRegistrationDto traderDto = new TraderRegistrationDto();
	    traderDto.setFirstName("ludvig");
	    traderDto.setLastName("bodmer");
	    traderDto.setEmail("wemen60755@gusronk.com");
	    traderDto.setPassword("12345678");
	    emailService.sendVerificationEmail(traderDto, token);
	} catch (MailException e) {
	    logger.error(e.getMessage());
	}
	return token;
    }
}
