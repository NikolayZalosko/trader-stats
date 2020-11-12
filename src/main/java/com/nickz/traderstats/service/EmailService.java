package com.nickz.traderstats.service;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import com.nickz.traderstats.dto.TraderRegistrationDto;

public interface EmailService {
    
    void sendVerificationEmail(TraderRegistrationDto traderDto, String token) throws MessagingException, MailException; 
}
