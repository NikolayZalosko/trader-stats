package com.nickz.traderstats.service;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import com.nickz.traderstats.dto.UserEmailSendingDto;

public interface EmailService {
    
    void sendVerificationEmail(UserEmailSendingDto userDto, String token) throws MessagingException, MailException;
    void sendPasswordResetEmail(UserEmailSendingDto userDto, String token) throws MessagingException, MailException;
}
