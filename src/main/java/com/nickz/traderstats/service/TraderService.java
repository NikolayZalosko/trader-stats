package com.nickz.traderstats.service;

import java.util.List;

import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.exception.TraderAlreadyExistsException;
import com.nickz.traderstats.model.Trader;

public interface TraderService {
    List<Trader> findAll();
    Trader findById(int traderId) throws ResourceNotFoundException;
    Trader findByEmail(String email) throws ResourceNotFoundException;
    Trader getOne(int traderId) throws ResourceNotFoundException; /* returns a proxy object */
    Trader save(TraderRegistrationDto traderDto) throws TraderAlreadyExistsException;
    Trader update(Trader trader) throws ResourceNotFoundException;
    void delete(int id) throws ResourceNotFoundException;
}
