package com.nickz.traderstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class TraderStatsApplication {

    public static void main(String[] args) {
	SpringApplication.run(TraderStatsApplication.class, args);
    }
}
