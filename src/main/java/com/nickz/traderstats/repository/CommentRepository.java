package com.nickz.traderstats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nickz.traderstats.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
    @Query(value = "SELECT * FROM comment WHERE trader_id = ?1", nativeQuery = true)
    List<Comment> findTraderComments(int traderId);
}
