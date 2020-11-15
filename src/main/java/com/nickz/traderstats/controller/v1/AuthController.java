package com.nickz.traderstats.controller.v1;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.dto.UserEmailSendingDto;
import com.nickz.traderstats.exception.EmailNotVerifiedException;
import com.nickz.traderstats.model.User;
import com.nickz.traderstats.model.UserStatus;
import com.nickz.traderstats.service.EmailService;
import com.nickz.traderstats.service.TokenService;
import com.nickz.traderstats.service.TraderService;
import com.nickz.traderstats.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@Slf4j
class AuthController {
    private TokenService tokenService;
    private UserService userService;
    private EmailService emailService;
    private TraderService traderService;

    @Value("${server.port}")
    private int PORT;

    private String HOST = "localhost";

    public AuthController(TokenService tokenService, UserService userService, EmailService emailService,
	    TraderService traderService) {
	this.tokenService = tokenService;
	this.userService = userService;
	this.emailService = emailService;
	this.traderService = traderService;
    }

    /*
     * Confirm user's email
     */
    @GetMapping("/confirm_email/{token}")
    public String confirmEmail(@PathVariable("token") String token) {
	int userId = tokenService.getUserId(token);
	User userToUpdate = userService.getOne(userId);
	userToUpdate.setStatus(UserStatus.ACTIVE);
	tokenService.delete(token);

	userService.update(userToUpdate);
	return "Email has been verified";
    }

    /*
     * Send email with password reset link
     */
    @PostMapping("/forgot_password")
    @ResponseStatus(value = HttpStatus.OK)
    public String sendEmailWithPasswordResetLink(
	    @RequestParam("email") @Email(message = "Not a valid email") String email) {
	User user = userService.findByEmail(email);
	if (user.getStatus() == UserStatus.EMAIL_NOT_VERIFIED) {
	    throw new EmailNotVerifiedException("Email is not verified");
	}
	UserEmailSendingDto userEmailDto = new UserEmailSendingDto();
	userEmailDto.setName(traderService.findByUserId(user.getId()).getFirstName());
	userEmailDto.setEmail(email);
	String token = tokenService.save(user.getId());
	try {
	    emailService.sendPasswordResetEmail(userEmailDto, token);
	} catch (MailException e) {
	    log.error("MailException occured: " + e.getMessage());
	} catch (MessagingException e) {
	    log.error("MessagingException occured: " + e.getMessage());
	}
	return "Email with password reset link has been sent";
    }

    /*
     * Return a link to reset password
     */
    @GetMapping("/forgot_password/check_token/{token}")
    @ResponseStatus(value = HttpStatus.OK)
    public String checkTokenAndProvideResetPasswordLink(@PathVariable("token") String token) {
	String link = HOST + ":" + PORT + "/api/v1/auth/forgot_password/reset";
	return "To reset password do a POST request with the following form-data: {"
		+ "token=" + token + ", newPassword=YOUR_NEW_PASSWORD}"
			+ "to the following path: "
		+ link;
    }

    /*
     * Reset password
     */
    @PostMapping("/forgot_password/reset")
    @ResponseStatus(value = HttpStatus.OK)
    public String resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
	int userId = tokenService.getUserId(token);
	userService.resetPassword(userId, newPassword);
	tokenService.delete(token);
	return "Password has been reset";
    }

}
