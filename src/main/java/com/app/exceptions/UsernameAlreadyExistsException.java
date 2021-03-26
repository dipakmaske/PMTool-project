package com.app.exceptions;

@SuppressWarnings("serial")
public class UsernameAlreadyExistsException extends RuntimeException{

	public UsernameAlreadyExistsException(String message) {
		super(message);
	
	}
	

}
