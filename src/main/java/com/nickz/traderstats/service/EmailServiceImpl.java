package com.nickz.traderstats.service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.nickz.traderstats.dto.TraderRegistrationDto;

@Service
public class EmailServiceImpl implements EmailService {
    
    private String HOST = "localhost";
    
    @Value("${server.port}")
    private String PORT;
    
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(TraderRegistrationDto traderDto, String token) throws MessagingException, MailException {
	MimeMessage message = mailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message, true);
	helper.setTo(traderDto.getEmail());
	helper.setSubject("Confirm your email");
	
	String link = HOST + ":" + PORT + "/api/v1/auth/confirm_email/" + token;
	String text = "Hello, " + traderDto.getFirstName() + "! To confirm your email in trader-stats app follow the link: "
		+ "<a href=\"" + link + "\">" + link + "</a>";
	helper.setText(text, true);
	
	
	/*
	SimpleMailMessage message = new SimpleMailMessage();
	message.setFrom("bot@traderstats.com");
	message.setTo(traderDto.getEmail());
	message.setSubject("Confirm your email");
	
	String link = HOST + ":" + PORT + "/auth/confirm_email/" + token;
	message.setText(text);
	*/
	
	mailSender.send(message);
    }
    
    
}
