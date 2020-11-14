package com.nickz.traderstats.service;

import java.util.List;

import com.nickz.traderstats.dto.TraderWithNoUserAttachedDto;
import com.nickz.traderstats.dto.TraderWithUserAttachedDto;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.Trader;

public interface TraderService {
    
    List<Trader> findAll();
    
    Trader findById(int traderId) throws ResourceNotFoundException;
    
    Trader getOne(int traderId) throws ResourceNotFoundException; /* returns a proxy object */
    
    Trader save(TraderWithUserAttachedDto traderDto);
    Trader saveWithNoUserAttached(TraderWithNoUserAttachedDto traderDto);
    
    Trader update(Trader trader) throws ResourceNotFoundException;
    
    void delete(int id) throws ResourceNotFoundException;
    void deleteAll();
}
