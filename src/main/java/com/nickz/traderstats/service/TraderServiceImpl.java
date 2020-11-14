package com.nickz.traderstats.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.nickz.traderstats.dto.TraderWithNoUserAttachedDto;
import com.nickz.traderstats.dto.TraderWithUserAttachedDto;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.repository.TraderRepository;

@Service
public class TraderServiceImpl implements TraderService {
    private TraderRepository traderRepository;

    public TraderServiceImpl(TraderRepository repository) {
	this.traderRepository = repository;
    }

    @Override
    public List<Trader> findAll() {
	return traderRepository.findAll();
    }

    @Override
    public Trader findById(int traderId) throws ResourceNotFoundException {
	Trader foundTrader = traderRepository.findById(traderId)
		.orElseThrow(() -> new ResourceNotFoundException("Trader with this ID doesn't exist"));
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
    public Trader save(TraderWithUserAttachedDto traderDto) {
	Trader trader = new Trader();
	trader.setFirstName(traderDto.getFirstName());
	trader.setLastName(traderDto.getLastName());
	trader.setUser(traderDto.getUser());
	trader.setIsApproved(null);
	return traderRepository.save(trader);
    }

    @Override
    public Trader saveWithNoUserAttached(TraderWithNoUserAttachedDto traderDto) {
	Trader trader = new Trader();
	trader.setFirstName(traderDto.getFirstName());
	trader.setLastName(traderDto.getLastName());
	trader.setIsApproved(null);
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
	traderRepository.findById(traderId)
		.orElseThrow(() -> new ResourceNotFoundException("Trader with this ID doesn't exist"));
	traderRepository.deleteById(traderId);
    }
    
    @Override
    public void deleteAll() {
	traderRepository.deleteAll();
    }
}
