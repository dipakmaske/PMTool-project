package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserRepository;
import com.app.exceptions.UsernameAlreadyExistsException;
import com.app.pojos.Role;
import com.app.pojos.User;

@Transactional
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User newUser) {

		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			// Username has to be unique (exception)
			newUser.setUsername(newUser.getUsername());
			// Make sure that password and confirmPassword match
			// We don't persist or show the confirmPassword
			newUser.setConfirmPassword("");
			System.out.println(newUser);
			return userRepository.save(newUser);

		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
		}

	}

	public Iterable<User> getAllDeveloper(String organizationName) {
		Role role = Role.DEVELOPER;
		return userRepository.findByRoleAndOrganizationName(role,organizationName);
	}


}
