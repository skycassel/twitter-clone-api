package com.cooksys.tweeter.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.tweeter.dtos.CreateUserRequestDto;
import com.cooksys.tweeter.dtos.CredentialsDto;
import com.cooksys.tweeter.dtos.TweetResponseDto;
import com.cooksys.tweeter.dtos.UserRequestDto;
import com.cooksys.tweeter.dtos.UserResponseDto;
import com.cooksys.tweeter.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<UserResponseDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/@{username}")
  public UserResponseDto getUserByUsername(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

  @PatchMapping("/@{username}")
  public UserResponseDto updateUser( @PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
    return userService.updateUser(username, userRequestDto);
  }

  @DeleteMapping("/@{username}")
  public UserResponseDto deleteUser( @PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
    return userService.deleteUser(username, credentialsDto);
  }

  @PostMapping
  public UserResponseDto createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
    return userService.createUser(createUserRequestDto);
  }
  
  @PostMapping("/@{username}/follow")
  public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
	  userService.followUser(username, credentialsDto);
  }
  
  @PostMapping("/@{username}/unfollow")
  public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
	  userService.unfollowUser(username, credentialsDto);
  }
  
  @GetMapping("/@{username}/followers")
  public List<UserResponseDto> getFollowers(@PathVariable String username) {
	  return userService.getFollowers(username);
  }
  
  @GetMapping("/@{username}/following")
  public List<UserResponseDto> getFollowing(@PathVariable String username) {
	  return userService.getFollowing(username);
  }

  @GetMapping("/@{username}/feed")
  public List<TweetResponseDto> getFeed(@PathVariable String username) {
    return userService.getFeed(username);
  }

  @GetMapping("/@{username}/tweets")
  public List<TweetResponseDto> getTweets(@PathVariable String username) {
    return userService.getTweets(username);
  }

  @GetMapping("/@{username}/mentions")
  public List<TweetResponseDto> getMentions(@PathVariable String username) {
    return userService.getMentions(username);
  }

}
