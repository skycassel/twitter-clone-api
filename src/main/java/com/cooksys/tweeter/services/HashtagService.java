package com.cooksys.tweeter.services;

import java.util.List;

import com.cooksys.tweeter.dtos.HashtagDto;
import com.cooksys.tweeter.dtos.TweetResponseDto;

public interface HashtagService {

	List<HashtagDto> getAllTags();

	List<TweetResponseDto> getTag(String label);

}
