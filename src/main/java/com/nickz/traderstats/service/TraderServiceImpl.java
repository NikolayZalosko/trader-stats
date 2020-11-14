package com.nickz.traderstats.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.nickz.traderstats.dto.TraderWithNoUserAttachedDto;
import com.nickz.traderstats.dto.TraderWithUserAttachedDto;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.repository.CommentRepository;
import com.nickz.traderstats.repository.TraderRepository;

@Service
public class TraderServiceImpl implements TraderService {
    private TraderRepository traderRepository;
    private CommentRepository commentRepository;

    public TraderServiceImpl(TraderRepository traderRepository, CommentRepository commentRepository) {
	this.traderRepository = traderRepository;
	this.commentRepository = commentRepository;
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
    public Trader updateRating(int traderId) {
	Trader trader = this.findById(traderId);
	Double newRating = this.calculateTraderRating(this.getTraderEstimates(traderId));
	trader.setRating(newRating);
	return this.update(trader);
    }
    
    private int[] getTraderEstimates(int traderId) {
	List<Integer> estimateList = commentRepository.findTraderComments(traderId).stream().map(comment -> comment.getRating())
		.collect(Collectors.toList());
	int[] estimateArray = estimateList.stream().mapToInt(e -> e).toArray();
	return estimateArray;
    }
    
    private Double calculateTraderRating(int[] estimates) {
	if (estimates.length == 0) {
	    return null;
	}
	BigDecimal bd = new BigDecimal(Arrays.stream(estimates).average().getAsDouble());
	bd = bd.setScale(3, RoundingMode.HALF_UP);
	return bd.doubleValue();
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
