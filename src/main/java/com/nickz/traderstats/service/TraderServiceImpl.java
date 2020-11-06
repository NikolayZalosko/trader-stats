package com.nickz.traderstats.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.TraderStatus;
import com.nickz.traderstats.repository.TraderRepository;

@Service
public class TraderServiceImpl implements TraderService {

    @Autowired
    private TraderRepository repository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @Override
    public List<Trader> findAll() {
	return repository.findAll();
    }
    
    @Override
    public Trader findById(int traderId) {
	return repository.getOne(traderId);
    }

    @Override
    public Trader save(TraderRegistrationDto traderDto) {
	Trader trader = new Trader();
	trader.setFirstName(traderDto.getFirstName());
	trader.setLastName(traderDto.getLastName());
	trader.setPassword(passwordEncoder.encode(traderDto.getPassword()));
	trader.setEmail(traderDto.getEmail());
	trader.setCreationDate(ZonedDateTime.now());
	trader.setStatus(TraderStatus.EMAIL_NOT_VERIFIED);
	return repository.save(trader);
    }

    @Override
    public Trader update(Trader trader) {
	return repository.save(trader);
    }

    @Override
    public boolean delete(int id) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Trader getOne(int traderId) {
	return repository.getOne(traderId);
    }

  

}
