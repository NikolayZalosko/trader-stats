package com.nickz.traderstats.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nickz.traderstats.dto.TraderRegistrationDto;
import com.nickz.traderstats.model.Trader;
import com.nickz.traderstats.service.TraderService;

@RestController
@RequestMapping("/api/v1/traders")
class TraderController {
    @Autowired
    TraderService service;
    
    @GetMapping
    public List<Trader> getAllTraders() {
	return service.findAll();
    }
    
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Trader create(@RequestBody @Valid TraderRegistrationDto traderDto) {
	return service.save(traderDto);
    }
    
}
