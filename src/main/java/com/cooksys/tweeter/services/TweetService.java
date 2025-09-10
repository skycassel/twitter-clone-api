package com.cooksys.tweeter.services;

import com.cooksys.tweeter.dtos.*;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getAllTweets();

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto getTweetById(Long id);

    TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto);

    void likeTweet(Long id, CredentialsDto credentialsDto);

    TweetResponseDto createReplyToTweet(Long id, TweetRequestDto tweetRequestDto);

    TweetResponseDto createRepostOfTweet(Long id, CredentialsDto credentialsDto);

    List<HashtagDto> getTagsFromTweet(Long id);

    List<UserResponseDto> getLikesFromTweet(Long id);

    ContextDto getContextFromTweet(Long id);

    List<TweetResponseDto> getRepliesOfTweet(Long id);

    List<TweetResponseDto> getRepostsOfTweet(Long id);

    List<UserResponseDto> getMentionsOfTweet(Long id);
}
