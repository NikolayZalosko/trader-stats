package com.nickz.traderstats.service;

import java.util.List;

import com.nickz.traderstats.dto.CommentCreationDto;
import com.nickz.traderstats.model.Comment;

public interface CommentService {
    
    List<Comment> findTraderComments(int traderId);

    Comment create(CommentCreationDto commentDto);
}
