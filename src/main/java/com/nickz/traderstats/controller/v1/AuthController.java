package com.nickz.traderstats.controller.v1;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.model.User;
import com.nickz.traderstats.model.UserStatus;
import com.nickz.traderstats.service.TokenService;
import com.nickz.traderstats.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
class AuthController {
    private TokenService tokenService;
    private UserService userService;

    public AuthController(TokenService tokenService, UserService userService) {
	this.tokenService = tokenService;
	this.userService = userService;
    }

    @GetMapping("/confirm_email/{token}")
    public String confirmEmail(@PathVariable String token) {
	int userId = Integer.valueOf(tokenService.getUserId(token));
	User userToUpdate = userService.getOne(userId);
	userToUpdate.setStatus(UserStatus.ACTIVE);
	tokenService.delete(token);

	userService.update(userToUpdate);
	return "Email has been verified";
    }

    /*
    @PostMapping("/save_token")
    public TraderToken saveToken(@RequestBody TraderToken token) {
	return tokenService.save(token, Duration.ofSeconds(5));
    }
    */
}
