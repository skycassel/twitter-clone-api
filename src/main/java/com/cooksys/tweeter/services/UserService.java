package com.cooksys.tweeter.services;

import java.util.List;

import com.cooksys.tweeter.dtos.CreateUserRequestDto;
import com.cooksys.tweeter.dtos.CredentialsDto;
import com.cooksys.tweeter.dtos.TweetResponseDto;
import com.cooksys.tweeter.dtos.UserRequestDto;
import com.cooksys.tweeter.dtos.UserResponseDto;

public interface UserService {

  List<UserResponseDto> getAllUsers();

  UserResponseDto getUserByUsername(String username);

  UserResponseDto updateUser(String username, UserRequestDto userRequestDto);

  UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

  UserResponseDto createUser(CreateUserRequestDto createUserRequestDto);

  void followUser(String username, CredentialsDto credentialsDto);

  List<UserResponseDto> getFollowers(String username);

  List<TweetResponseDto> getFeed(String username);

  List<TweetResponseDto> getTweets(String username);

  void unfollowUser(String username, CredentialsDto credentialsDto);

  List<UserResponseDto> getFollowing(String username);

  List<TweetResponseDto> getMentions(String username);

}
