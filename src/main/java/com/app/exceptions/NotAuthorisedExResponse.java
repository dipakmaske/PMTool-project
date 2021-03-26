package com.app.exceptions;

public class NotAuthorisedExResponse {

private String role;


public NotAuthorisedExResponse(String role) {
	super();
	this.role = role;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}
	
	
}
