package com.nickz.traderstats.service;

import java.util.List;

import com.nickz.traderstats.dto.CommentCreationDto;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.Comment;

public interface CommentService {
    
    List<Comment> findTraderComments(int traderId) throws ResourceNotFoundException;
    
    Comment findById(int commentId) throws ResourceNotFoundException;

    Comment save(CommentCreationDto commentDto, Integer traderId);
    
    Comment update(Comment updatedComment);
}
