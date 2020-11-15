package com.nickz.traderstats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.Trader;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {
    
    @Query(value = "SELECT * FROM trader WHERE status = 'APPROVED'", nativeQuery = true)
    List<Trader> findAllApproved();
    
    @Query(value = "SELECT * FROM trader WHERE user_id = :userId", nativeQuery = true)
    Trader findByUserId(@Param("userId") int userId);
    
    @Query(value = "SELECT * FROM trader WHERE user_id = (SELECT id FROM user WHERE email = :email)", nativeQuery = true)
    Trader findByUserEmail(@Param("email") String email);
    
    @Query(value = "SELECT * FROM trader ORDER BY rating DESC LIMIT :number", nativeQuery = true)
    List<Trader> findTopTraders(@Param("number") int number);
}
