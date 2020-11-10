package com.nickz.traderstats.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraderRegistrationDto {
    @NotBlank(message = "First name field can't be blank")
    private String firstName;
    
    @NotBlank(message = "Last name field can't be blank")
    private String lastName;
    
    @Email(message = "Incorrect email field")
    private String email;
    
    @NotBlank(message = "Password field can't be blank")
    private String password;
}
