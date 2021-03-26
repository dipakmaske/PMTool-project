package com.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAuthorisedException extends RuntimeException {

	public NotAuthorisedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
