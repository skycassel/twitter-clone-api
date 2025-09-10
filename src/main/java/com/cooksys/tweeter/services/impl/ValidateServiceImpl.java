package com.cooksys.tweeter.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.tweeter.embeddables.Credentials;
import com.cooksys.tweeter.entities.User;
import com.cooksys.tweeter.exceptions.BadRequestException;
import com.cooksys.tweeter.exceptions.NotAuthorizedException;
import com.cooksys.tweeter.exceptions.NotFoundException;
import com.cooksys.tweeter.repositories.HashtagRepository;
import com.cooksys.tweeter.repositories.UserRepository;
import com.cooksys.tweeter.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final HashtagRepository hashtagRepository;
	private final UserRepository userRepository;
	
	public boolean tagExists(String label) {
		return hashtagRepository.findByLabel(label) != null;
	}

	@Override
	public boolean usernameExists(String username) {
		return !userRepository.findByCredentialsUsernameAndDeletedFalse(username).isEmpty();
	}

	@Override
	public boolean usernameAvailable(String username) {
		// What makes a username available?
		return userRepository.findByCredentialsUsernameAndDeletedFalse(username).isEmpty();
	}

	@Override
	public User authenticateAndReturnUser(Credentials credentials) {
		if (credentials == null) {
			throw new BadRequestException("Username and Password are required");
		}
		if (credentials.getUsername() == null || credentials.getUsername().isEmpty()) {
			throw new BadRequestException("Username is required");
		}
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("User with username: " + credentials.getUsername() + " was not found.");
		}
		User user = optionalUser.get();
		if (!user.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Username or password is incorrect.");
		}
//		if (!user.getCredentials().getUsername().equals(credentials.getUsername())) {
//			throw new NotAuthorizedException("Username or password is incorrect.");
//		}
		return user;
	}

}
