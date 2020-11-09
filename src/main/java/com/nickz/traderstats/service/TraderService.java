package com.nickz.traderstats.service;

import java.util.List;

import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.model.Trader;

public interface TraderService {
    List<Trader> findAll();

    Trader findById(int traderId);

    Trader getOne(int traderId); /* returns a proxy object */

    Trader save(TraderRegistrationDto traderDto);

    Trader update(Trader trader);

    boolean delete(int id);
}
