package com.nickz.traderstats.service;

import com.nickz.traderstats.dto.TraderRegistrationDto;

public interface EmailService {
    void sendVerificationEmail(TraderRegistrationDto traderDto, String token);
}
