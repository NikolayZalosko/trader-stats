package com.nickz.traderstats.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nickz.traderstats.dto.CommentCreationDto;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.model.CommentStatus;
import com.nickz.traderstats.repository.CommentRepository;
import com.nickz.traderstats.repository.TraderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private TraderRepository traderRepository;

    public CommentServiceImpl(CommentRepository commentRepository, TraderRepository traderRepository) {
	this.commentRepository = commentRepository;
	this.traderRepository = traderRepository;
    }

    @Override
    public List<Comment> findTraderComments(int traderId) {
	traderRepository.findById(traderId)
		.orElseThrow(() -> new ResourceNotFoundException("Trader with this ID doesn't exist"));
	return commentRepository.findTraderComments(traderId);
    }

    @Override
    public Comment findById(int commentId) throws ResourceNotFoundException {
	return commentRepository.findById(commentId)
		.orElseThrow(() -> new ResourceNotFoundException("Comment with this ID doesn't exist"));
    }

    @Override
    public Comment save(CommentCreationDto commentDto, Integer traderId) {
	Comment comment = new Comment();
	/*
	comment.setTrader(traderRepository.findById(traderId)
		.orElseThrow(() -> new ResourceNotFoundException("Trader with this ID doesn't exist")));
	*/
	comment.setTraderId(traderId);
	comment.setRating(commentDto.getRating());
	comment.setMessage(commentDto.getMessage());
	comment.setCreationDate(LocalDateTime.now());
	comment.setStatus(CommentStatus.NOT_APPROVED_YET);
	return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment updatedComment) {
	this.findById(updatedComment.getId());
	return commentRepository.save(updatedComment);
    }

}
