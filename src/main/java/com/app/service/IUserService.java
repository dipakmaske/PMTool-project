package com.app.service;

import com.app.pojos.User;

public interface IUserService {

	public User saveUser(User newUser);

	public Iterable<User> getAllDeveloper(String organizationName);
	
}
