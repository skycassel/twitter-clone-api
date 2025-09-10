package com.cooksys.tweeter.controllers;

import com.cooksys.tweeter.dtos.*;
import com.cooksys.tweeter.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.deleteTweetById(id, credentialsDto);
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public void likeTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        tweetService.likeTweet(id, credentialsDto);
    }

    @PostMapping("/{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createReplyToTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createReplyToTweet(id, tweetRequestDto);
    }

    @PostMapping("/{id}/repost")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createRepostOfTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.createRepostOfTweet(id, credentialsDto);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTagsFromTweet(@PathVariable Long id) {
        return tweetService.getTagsFromTweet(id);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getLikesFromTweet(@PathVariable Long id) {
        return tweetService.getLikesFromTweet(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getContextFromTweet(@PathVariable Long id) {
        return tweetService.getContextFromTweet(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getRepliesOfTweet(@PathVariable Long id) {
        return tweetService.getRepliesOfTweet(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getRepostsOfTweet(@PathVariable Long id) {
        return tweetService.getRepostsOfTweet(id);
    }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentionsOfTweet(@PathVariable Long id) {
        return tweetService.getMentionsOfTweet(id);
    }
}
