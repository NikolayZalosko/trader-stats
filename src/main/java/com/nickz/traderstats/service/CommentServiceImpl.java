package com.nickz.traderstats.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nickz.traderstats.dto.CommentCreationDto;
import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
    
    @Autowired
    private CommentRepository repository;
    
    @Autowired
    private TraderService traderService;

    @Override
    public List<Comment> findTraderComments(int traderId) {
	return repository.findTraderComments(traderId);
    }

    @Override
    public Comment create(CommentCreationDto commentDto) {
	Comment comment = new Comment();
	comment.setTrader(traderService.findById(commentDto.getTraderId()));
	comment.setRating(commentDto.getRating());
	comment.setMessage(commentDto.getMessage());
	comment.setCreationDate(ZonedDateTime.now());
	comment.setApproved(false);
	return repository.save(comment);
    }

}
