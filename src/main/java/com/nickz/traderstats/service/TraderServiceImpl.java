package com.nickz.traderstats.service;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.exception.TraderAlreadyExistsException;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.TraderStatus;
import com.nickz.traderstats.repository.TraderRepository;

@Service
public class TraderServiceImpl implements TraderService {
    private TraderRepository traderRepository;
    private PasswordEncoder passwordEncoder;

    public TraderServiceImpl(TraderRepository repository, PasswordEncoder passwordEncoder) {
	this.traderRepository = repository;
	this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Trader> findAll() {
	return traderRepository.findAll();
    }

    @Override
    public Trader findById(int traderId) throws ResourceNotFoundException {
	Trader foundTrader = traderRepository.findById(traderId)
		.orElseThrow(() -> new ResourceNotFoundException("Trader with this id doesn't exist"));
	return foundTrader;
    }

    @Override
    public Trader findByEmail(String email) throws ResourceNotFoundException {
	Trader foundTrader = traderRepository.findByEmail(email);
	if (foundTrader == null) {
	    throw new ResourceNotFoundException("Trader with this email doesn't exist");
	}
	return foundTrader;
    }

    @Override
    public Trader getOne(int traderId) throws ResourceNotFoundException {
	try {
	    return traderRepository.getOne(traderId);
	} catch (EntityNotFoundException e) {
	    throw new ResourceNotFoundException("Entity with this id doesn't exist");
	}
    }

    @Override
    public Trader save(TraderRegistrationDto traderDto) throws TraderAlreadyExistsException {
	Trader existingTrader = this.findByEmail(traderDto.getEmail());
	if (existingTrader != null) {
	    throw new TraderAlreadyExistsException("Trader with this email already exists");
	}

	Trader trader = new Trader();
	trader.setFirstName(traderDto.getFirstName());
	trader.setLastName(traderDto.getLastName());
	trader.setPassword(passwordEncoder.encode(traderDto.getPassword()));
	trader.setEmail(traderDto.getEmail());
	trader.setCreationDate(ZonedDateTime.now());
	trader.setStatus(TraderStatus.EMAIL_NOT_VERIFIED);

	return traderRepository.save(trader);
    }

    @Override
    public Trader update(Trader trader) {
	traderRepository.findById(trader.getId())
		.orElseThrow(() -> new ResourceNotFoundException("Trader doesn't exist"));
	return traderRepository.save(trader);
    }

    @Override
    public void delete(int traderId) {
	traderRepository.deleteById(traderId);
	;
    }
}
