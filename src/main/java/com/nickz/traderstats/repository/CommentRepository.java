package com.nickz.traderstats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
    @Query(value = "SELECT * FROM comment WHERE trader_id = :traderId", nativeQuery = true)
    List<Comment> findTraderComments(@Param("traderId") int traderId);
    
    @Query(value = "SELECT * FROM comment WHERE trader_id = :traderId AND status = 'APPROVED'", nativeQuery = true)
    List<Comment> findTraderApprovedComments(@Param("traderId") int traderId);
    
}
