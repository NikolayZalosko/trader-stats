package com.nickz.traderstats.controller.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.model.CommentStatus;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.User;
import com.nickz.traderstats.model.UserStatus;
import com.nickz.traderstats.service.CommentService;
import com.nickz.traderstats.service.TraderService;
import com.nickz.traderstats.service.UserService;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {

    private UserService userService;
    private TraderService traderService;
    private CommentService commentService;

    public AdminController(UserService userService, TraderService traderService, CommentService commentService) {
	this.userService = userService;
	this.traderService = traderService;
	this.commentService = commentService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
	return userService.findAll();
    }

    /*
     * Approve a trader
     */
    @GetMapping("/traders/{traderId}/approve")
    public Trader approveTrader(@PathVariable int traderId) {
	Trader traderToUpdate = traderService.findById(traderId);
	traderToUpdate.setIsApproved(true);
	return traderService.update(traderToUpdate);
    }

    /*
     * Decline a trader
     */
    @GetMapping("/traders/{traderId}/decline")
    public Trader declineTrader(@PathVariable int traderId) {
	Trader traderToUpdate = traderService.findById(traderId);
	traderToUpdate.setIsApproved(false);
	return traderService.update(traderToUpdate);
    }

    /*
     * Get trader's full information
     */
    @GetMapping("/traders")
    public List<Trader> getAllTraders() {
	return traderService.findAll();
    }

    /*
     * Delete a trader
     */
    @DeleteMapping("/traders/{traderId}")
    public String deleteTrader(@PathVariable int traderId) {
	traderService.delete(traderId);
	return "Trader has been deleted";
    }
    
    /*
     * Delete all trader
     */
    @DeleteMapping("/traders/deleteAll")
    public String deleteAllTraders() {
	traderService.deleteAll();
	return "All traders have been deleted";
    }

    /*
     * Approve a comment
     */
    @GetMapping("/comments/{commentId}/approve")
    public Comment approveComment(@PathVariable int commentId) {
	Comment commentToUpdate = commentService.findById(commentId);
	commentToUpdate.setStatus(CommentStatus.APPROVED);
	return commentService.update(commentToUpdate);
    }

    /*
     * Decline a comment
     */
    @GetMapping("/comments/{commentId}/decline")
    public Comment declineComment(@PathVariable int commentId) {
	Comment commentToUpdate = commentService.findById(commentId);
	commentToUpdate.setStatus(CommentStatus.DECLINED);
	return commentService.update(commentToUpdate);
    }
}
