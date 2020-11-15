package com.nickz.traderstats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.model.Trader;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {
    
    @Query(value = "SELECT * FROM trader WHERE status = 'APPROVED'", nativeQuery = true)
    List<Trader> findAllApproved();
}
