package com.cooksys.tweeter.services;

import org.springframework.stereotype.Service;

import com.cooksys.tweeter.embeddables.Credentials;
import com.cooksys.tweeter.entities.User;

public interface ValidateService {
	
	public boolean tagExists(String label);

	public boolean usernameExists(String username);

	public boolean usernameAvailable(String username);

	public User authenticateAndReturnUser(Credentials credentials);
}
