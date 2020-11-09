package com.nickz.traderstats.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.dto.CommentCreationDto;
import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.service.CommentService;

@RestController
@RequestMapping("/api/v1")
class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
	this.commentService = commentService;
    }

    @GetMapping("/traders/{traderId}/comments")
    public List<Comment> getTraderComments(@PathVariable(name = "traderId") int traderId) {
	return commentService.findTraderComments(traderId);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/traders/{traderId}/comments")
    public Comment createComment(@PathVariable(name = "traderId") int traderId,
	    @RequestBody @Valid CommentCreationDto commentDto) {
	return commentService.create(commentDto);
    }
}
