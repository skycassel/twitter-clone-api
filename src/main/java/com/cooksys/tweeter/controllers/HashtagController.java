package com.cooksys.tweeter.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.tweeter.services.HashtagService;
import com.cooksys.tweeter.dtos.HashtagDto;
import com.cooksys.tweeter.dtos.TweetResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
	
	private final HashtagService hashtagService;
	
	@GetMapping
	public List<HashtagDto> getAllTags() {
		return hashtagService.getAllTags();
	}

	@GetMapping("{label}")
	public List<TweetResponseDto> getTag(@PathVariable String label) {
		return hashtagService.getTag(label);
	}
	
}
