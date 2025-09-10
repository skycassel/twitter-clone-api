package com.cooksys.tweeter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.tweeter.services.ValidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {
	
	private final ValidateService validateService;
	
	@GetMapping("/tag/exists/{label}")
	public boolean tagExists(@PathVariable String label) {
		return validateService.tagExists(label);
	}
	
	@GetMapping("/username/exists/@{username}")
	public boolean usernameExists(@PathVariable String username) {
		return validateService.usernameExists(username);
	}
	
	@GetMapping("/username/available/@{username}")
	public boolean usernameAvailable(@PathVariable String username) {
		return validateService.usernameAvailable(username);
	}

}
