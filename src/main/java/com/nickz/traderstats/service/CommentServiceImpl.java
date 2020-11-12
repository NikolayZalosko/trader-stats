package com.nickz.traderstats.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nickz.traderstats.dto.CommentCreationDto;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.model.CommentStatus;
import com.nickz.traderstats.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository repository) {
	this.commentRepository = repository;
    }

    @Override
    public List<Comment> findTraderComments(int traderId) {
	return commentRepository.findTraderComments(traderId);
    }

    @Override
    public Comment findById(int commentId) throws ResourceNotFoundException {
	return commentRepository.findById(commentId)
		.orElseThrow(() -> new ResourceNotFoundException("Comment with this ID doesn't exist"));
    }

    @Override
    public Comment create(CommentCreationDto commentDto) {
	Comment comment = new Comment();
//	comment.setTrader(traderService.findById(commentDto.getTraderId()));
	comment.setTraderId(commentDto.getTraderId());
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
