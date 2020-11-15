package com.nickz.traderstats.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.model.Comment;
import com.nickz.traderstats.model.CommentStatus;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.model.TraderStatus;
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

    /*
     * Get all users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
	return userService.findAll();
    }

    /*
     * Get all traders with full info
     */
    @GetMapping("/traders")
    public List<Trader> getAllTraders(@RequestParam(name = "filter") String filter) {
	List<Trader> result = traderService.findAll();
	if (filter.equals("email_verified")) {
	    result = result.stream().filter(trader -> {
		if (trader.getUser() == null) {
		    return true;
		}
		if (trader.getUser().getStatus() == UserStatus.ACTIVE) {
		    return true;
		}
		return false;
	    }).collect(Collectors.toList());
	}
	return result;
    }

    /*
     * Approve a trader
     */
    @PatchMapping("/traders/{traderId}/approve")
    @ResponseStatus(value = HttpStatus.OK)
    public String approveTrader(@PathVariable int traderId) {
	Trader traderToUpdate = traderService.findById(traderId);
	traderToUpdate.setStatus(TraderStatus.APPROVED);
	traderService.update(traderToUpdate);
	return "Trader has been approved";
    }

    /*
     * Decline a trader
     */
    @PatchMapping("/traders/{traderId}/decline")
    @ResponseStatus(value = HttpStatus.OK)
    public String declineTrader(@PathVariable int traderId) {
	Trader traderToUpdate = traderService.findById(traderId);
	traderToUpdate.setStatus(TraderStatus.DECLINED);
	traderService.update(traderToUpdate);
	return "Trader has been declined";
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
     * Delete all traders
     */
    @DeleteMapping("/traders/deleteAll")
    public String deleteAllTraders() {
	traderService.deleteAll();
	return "All traders have been deleted";
    }

    /*
     * Get trader's comments full list
     */
    @GetMapping("/traders/{traderId}/comments")
    public List<Comment> getTraderComments(@PathVariable(name = "traderId") int traderId) {
	return commentService.findTraderComments(traderId);
    }

    /*
     * Approve a comment and update trader's rating
     */
    @PatchMapping("/comments/{commentId}/approve")
    @ResponseStatus(value = HttpStatus.OK)
    public String approveComment(@PathVariable int commentId) {
	Comment commentToUpdate = commentService.findById(commentId);
	commentToUpdate.setStatus(CommentStatus.APPROVED);
	traderService.updateRating(commentToUpdate.getTrader().getId());
	commentService.update(commentToUpdate);
	return "Comment has been approved";
    }

    /*
     * Decline a comment
     */
    @PatchMapping("/comments/{commentId}/decline")
    @ResponseStatus(value = HttpStatus.OK)
    public String declineComment(@PathVariable int commentId) {
	Comment commentToUpdate = commentService.findById(commentId);
	commentToUpdate.setStatus(CommentStatus.DECLINED);
	commentService.update(commentToUpdate);
	return "Comment has been declined";
    }
}
