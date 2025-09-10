package com.cooksys.tweeter.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.tweeter.dtos.HashtagDto;
import com.cooksys.tweeter.dtos.TweetResponseDto;
import com.cooksys.tweeter.entities.Hashtag;
import com.cooksys.tweeter.exceptions.BadRequestException;
import com.cooksys.tweeter.mappers.HashtagMapper;
import com.cooksys.tweeter.mappers.TweetMapper;
import com.cooksys.tweeter.repositories.HashtagRepository;
import com.cooksys.tweeter.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	private final TweetMapper tweetMapper;
	
	@Override
	public List<HashtagDto> getAllTags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	@Override
	public List<TweetResponseDto> getTag(String label) {
		Optional<Hashtag> optionalTag = hashtagRepository.findOptionalByLabel(label);

		if (optionalTag.isEmpty()) {
			throw new BadRequestException("Hashtag: " + label + " was not found.");
		}

		Hashtag hashtag = optionalTag.get();

		return tweetMapper.entitiesToDtos(hashtag.getTweets());
	}

}
