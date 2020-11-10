package com.nickz.traderstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.Trader;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {
    @Query(value = "SELECT * FROM trader WHERE email = ?1", nativeQuery = true)
    Trader findByEmail(String email);
}
