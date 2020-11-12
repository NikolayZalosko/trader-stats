package com.nickz.traderstats.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.model.CommentStatus;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.TraderStatus;
import com.nickz.traderstats.service.CommentService;
import com.nickz.traderstats.service.TraderService;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {

    private TraderService traderService;

    private CommentService commentService;

    public AdminController(TraderService traderService, CommentService commentService) {
	this.traderService = traderService;
	this.commentService = commentService;
    }

    @GetMapping("/approve_trader")
    public Trader approveTrader(@RequestParam int traderId) {
	Trader traderToUpdate = traderService.findById(traderId);
	traderToUpdate.setStatus(TraderStatus.ACTIVE);
	return traderService.update(traderToUpdate);
    }

    @GetMapping("/decline_trader")
    public Trader declineTrader(@RequestParam int traderId) {
	Trader traderToUpdate = traderService.findById(traderId);
	traderToUpdate.setStatus(TraderStatus.DECLINED);
	return traderService.update(traderToUpdate);
    }

    @GetMapping("/approve_comment")
    public Comment approveComment(@RequestParam int commentId) {
	Comment commentToUpdate = commentService.findById(commentId);
	commentToUpdate.setStatus(CommentStatus.APPROVED);
	return commentService.update(commentToUpdate);
    }
    
    @GetMapping("/decline_comment")
    public Comment declineComment(@RequestParam int commentId) {
	Comment commentToUpdate = commentService.findById(commentId);
	commentToUpdate.setStatus(CommentStatus.DECLINED);
	return commentService.update(commentToUpdate);
    }
}
