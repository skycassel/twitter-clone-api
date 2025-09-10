package com.cooksys.tweeter.mappers;


import com.cooksys.tweeter.dtos.TweetRequestDto;
import com.cooksys.tweeter.dtos.TweetResponseDto;
import com.cooksys.tweeter.entities.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TweetMapper {

    List<TweetResponseDto> entitiesToDtos(List<Tweet> tweet);

    Tweet tweetRequestToEntity(TweetRequestDto tweetRequestDto);

//    @Mapping(target = "author", source = "author")
    TweetResponseDto entityToResponseDto(Tweet tweet);
}
