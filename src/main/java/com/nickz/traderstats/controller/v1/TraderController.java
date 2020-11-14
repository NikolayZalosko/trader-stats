package com.nickz.traderstats.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.dto.TraderBasicInfoDto;
import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.dto.TraderWithUserAttachedDto;
import com.nickz.traderstats.dto.UserEmailSendingDto;
import com.nickz.traderstats.dto.UserSavingDto;
import com.nickz.traderstats.exception.EmailAlreadyExistsException;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.User;
import com.nickz.traderstats.service.EmailService;
import com.nickz.traderstats.service.TokenService;
import com.nickz.traderstats.service.TraderService;
import com.nickz.traderstats.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/traders")
@CrossOrigin
@Slf4j
class TraderController {
    private TraderService traderService;
    private UserService userService;
    private TokenService tokenService;
    private EmailService emailService;

    public TraderController(TraderService traderService, UserService userService, TokenService tokenService,
	    EmailService emailService) {
	this.traderService = traderService;
	this.userService = userService;
	this.tokenService = tokenService;
	this.emailService = emailService;
    }

    /*
     * Get trader with firstName and lastName fields only
     */
    @GetMapping
    public List<TraderBasicInfoDto> getAllTraders() {
	return traderService.findAll().stream()
		.map(trader -> new TraderBasicInfoDto(trader.getId(), trader.getFirstName(), trader.getLastName()))
		.collect(Collectors.toList());
    }

    /*
     * Register a trader account and send a verification email
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trader create(@RequestBody @Valid TraderRegistrationDto traderRegistrationDto)
	    throws EmailAlreadyExistsException {
	// Construct a user DTO and save a User
	UserSavingDto userDto = new UserSavingDto();
	userDto.setEmail(traderRegistrationDto.getEmail());
	userDto.setPassword(traderRegistrationDto.getPassword());
	userDto.setRole("ROLE_TRADER");
	User savedUser = userService.save(userDto);

	// Construct a trader DTO and save a trader with a user attached
	TraderWithUserAttachedDto traderWithUserDto = new TraderWithUserAttachedDto();
	traderWithUserDto.setFirstName(traderRegistrationDto.getFirstName());
	traderWithUserDto.setLastName(traderRegistrationDto.getLastName());
	traderWithUserDto.setUser(savedUser);
	Trader savedTrader = traderService.save(traderWithUserDto);

	// Send a verification email
	try {
	    String token = tokenService.save(savedUser.getId());
	    UserEmailSendingDto userEmailDto = new UserEmailSendingDto();
	    userEmailDto.setName(traderRegistrationDto.getFirstName());
	    userEmailDto.setEmail(traderRegistrationDto.getEmail());
	    emailService.sendVerificationEmail(userEmailDto, token);
	} catch (MailException e) {
	    log.debug(e.getMessage());
	} catch (MessagingException e) {
	    log.debug(e.getMessage());
	}

	return savedTrader;
    }

}
