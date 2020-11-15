package com.nickz.traderstats.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.dto.CommentAndTraderCreationDto;
import com.nickz.traderstats.dto.CommentCreationDto;
import com.nickz.traderstats.dto.TraderWithNoUserAttachedDto;
import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.service.CommentService;
import com.nickz.traderstats.service.TraderService;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class CommentController {
    private CommentService commentService;
    private TraderService traderService;

    public CommentController(CommentService commentService, TraderService traderService) {
	this.commentService = commentService;
	this.traderService = traderService;
    }

    /*
     * Get trader's comments which are approved by admin
     */
    @GetMapping("/traders/{traderId}/comments")
    public List<Comment> getTraderComments(@PathVariable(name = "traderId") int traderId) {
	return commentService.findTraderApprovedComments(traderId);
    }

    /*
     * Create a comment to an existing trader
     */
    @PostMapping("/traders/{traderId}/comments")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Comment createComment(@PathVariable(name = "traderId") int traderId,
	    @RequestBody @Valid CommentCreationDto commentDto) {
	return commentService.save(commentDto, traderId);
    }

    /*
     * Create a comment to a new trader (the account for a trader is not created, only the Trader object)
     */
    @PostMapping("/traders/new_trader/comments")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Comment createCommentToANewTrader(@RequestBody @Valid CommentAndTraderCreationDto commentTraderDto) {
	TraderWithNoUserAttachedDto traderDto = new TraderWithNoUserAttachedDto();
	traderDto.setFirstName(commentTraderDto.getTraderFirstName());
	traderDto.setLastName(commentTraderDto.getTraderLastName());
	Trader savedTrader = traderService.saveWithNoUserAttached(traderDto);

	CommentCreationDto commentDto = new CommentCreationDto();
	commentDto.setRating(commentTraderDto.getRating());
	commentDto.setMessage(commentTraderDto.getMessage());
	Comment savedComment = commentService.save(commentDto, savedTrader.getId());

	return savedComment;

    }
}
