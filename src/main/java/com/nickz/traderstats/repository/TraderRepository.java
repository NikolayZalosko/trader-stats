package com.nickz.traderstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.Trader;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {
}
