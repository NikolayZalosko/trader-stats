package com.nickz.traderstats.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.admin.username}")
    private String adminUsername;
    
    @Value("${spring.security.admin.password}")
    private String adminPassword;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(adminUsername).password(passwordEncoder().encode(adminPassword)).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http
		.csrf().disable()
		.cors().and()
		.authorizeRequests().antMatchers("/api/v1/comments/**").permitAll()	
		.antMatchers("/api/v1/traders/**").permitAll()
		.antMatchers("/api/v1/admin/**").hasRole("ADMIN")
		.and().httpBasic();
    }
}
