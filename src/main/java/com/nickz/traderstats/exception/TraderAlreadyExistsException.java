package com.nickz.traderstats.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class TraderAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public TraderAlreadyExistsException(String message) {
	super(message);
    }
}
