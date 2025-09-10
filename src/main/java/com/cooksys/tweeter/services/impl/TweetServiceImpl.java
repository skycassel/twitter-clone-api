package com.cooksys.tweeter.services.impl;

import com.cooksys.tweeter.dtos.*;
import com.cooksys.tweeter.embeddables.Credentials;
import com.cooksys.tweeter.entities.Hashtag;
import com.cooksys.tweeter.entities.Tweet;
import com.cooksys.tweeter.entities.User;
import com.cooksys.tweeter.exceptions.BadRequestException;
import com.cooksys.tweeter.exceptions.NotAuthorizedException;
import com.cooksys.tweeter.exceptions.NotFoundException;
import com.cooksys.tweeter.mappers.CredentialsMapper;
import com.cooksys.tweeter.mappers.HashtagMapper;
import com.cooksys.tweeter.mappers.TweetMapper;
import com.cooksys.tweeter.mappers.UserMapper;
import com.cooksys.tweeter.repositories.HashtagRepository;
import com.cooksys.tweeter.repositories.TweetRepository;
import com.cooksys.tweeter.repositories.UserRepository;
import com.cooksys.tweeter.services.TweetService;
import com.cooksys.tweeter.services.UserService;
import com.cooksys.tweeter.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final ValidateService validateService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final HashtagMapper hashtagMapper;
    private final HashtagRepository hashtagRepository;
    private final CredentialsMapper credentialsMapper;

    private void validateUser(CredentialsDto credentialsDto) {
        if (credentialsDto == null) {
            throw new BadRequestException("Credentials not found");
        }
        String name = credentialsDto.getUsername();
        // System.out.println(credentialsDto);
        if (!validateService.usernameExists(name)) {
            // Wasn't exactly sure what exception to throw
            throw new NotAuthorizedException("Not a valid user. No user with the name " + name + " exists");
        }
        String password = credentialsDto.getPassword();
        if (password == null || password.isEmpty()) {
            throw new NotAuthorizedException("No password found");
        }
    }

    // might be a redundant method, but we'll see
    private void validateAuthor(Tweet tweet, CredentialsDto credentialsDto) {
        String name = credentialsDto.getUsername();
        User tweetAuthor = tweet.getAuthor();
        User author = userRepository.findByCredentialsUsernameAndDeletedFalse(name).get();
        if (!tweetAuthor.equals(author)) {
            throw new NotAuthorizedException("Credentials do not match author of tweet.");
        }
    }

    private void processContent(String content, Tweet tweet) {
        String[] words = content.split(" ");
        List<Hashtag> hashtags = new ArrayList<>();
        List<User> mentions = new ArrayList<>();
        for (String w : words) {
            if (w.startsWith("#") && w.length() > 1) {
                String label = w.substring(1);
                Optional<Hashtag> optionalTag = hashtagRepository.findOptionalByLabel(label);
                Hashtag tag = null;
                List<Tweet> tagTweets = null;
                if (optionalTag.isEmpty()) {
                    tag = new Hashtag();
                    tag.setLabel(label);
                    tagTweets = new ArrayList<>();
                } else {
                    tag = optionalTag.get();
                    tagTweets = tag.getTweets();
                }
                tag.setLastUsed(Timestamp.from(Instant.now()));
                tagTweets.add(tweet);
                tag.setTweets(tagTweets);
                hashtagRepository.saveAndFlush(tag);
                hashtags.add(tag);
            } else if (w.startsWith("@") && w.length() > 1) {

                String username = w.substring(1);
                User mentioned = userRepository.findByCredentialsUsernameAndDeletedFalse(username).get();
                mentions.add(mentioned);
            }
        }
        tweet.setHashtags(hashtags);
        tweet.setMentions(mentions);
        // tweetRepository.saveAndFlush(tweet);
        // tweet;
    }

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalseOrderByPostedDesc());
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
//        Tweet tweet = tweetMapper.tweetRequestToEntity(tweetRequestDto);
        Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());
        User user = validateService.authenticateAndReturnUser(credentials);
        String content = tweetRequestDto.getContent();
        if (content == null || content.isEmpty()) {
            throw new BadRequestException("Tweet content is empty");
        }
        Tweet tweet = new Tweet();
        tweet.setContent(content);
        tweet.setAuthor(user);
        tweet.setDeleted(false);
        tweet.setPosted(Timestamp.from(Instant.now()));
        processContent(content, tweet);
        return tweetMapper.entityToResponseDto(tweetRepository.save(tweet));
    }

    private Tweet getTweet(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("No tweet found with id: " + id);
        }
        return optionalTweet.get();
    }

    @Override
    public TweetResponseDto getTweetById(Long id) {
        return tweetMapper.entityToResponseDto(getTweet(id));
    }

    @Override
    public TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto) {
        validateUser(credentialsDto);
        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("No tweet found with id: " + id);
        }
        Tweet tweet = optionalTweet.get();
        validateAuthor(tweet, credentialsDto);
        tweet.setDeleted(true);
        return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public void likeTweet(Long id, CredentialsDto credentialsDto) {
        Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
        User user = validateService.authenticateAndReturnUser(credentials);
        Tweet tweet = getTweet(id);
        List<User> likes = tweet.getLikes();
        if (likes.contains(user)) {

        } else {
            likes.add(user);
            tweet.setLikes(likes);
            tweetRepository.saveAndFlush(tweet);
        }
    }

    // NEED TO PROCESS CONTENT FOR HASHTAGS
    @Override
    public TweetResponseDto createReplyToTweet(Long id, TweetRequestDto tweetRequestDto) {
        validateUser(tweetRequestDto.getCredentials());

        Tweet newReply = new Tweet();
        newReply.setContent(tweetRequestDto.getContent());
//        System.out.println(tweetRequestDto.getContent());

        Tweet ogTweet = getTweet(id);
        newReply.setInReplyTo(ogTweet);

        newReply.setDeleted(false);

        User author = userRepository.findByCredentialsUsernameAndDeletedFalse(tweetRequestDto.getCredentials().getUsername()).get();
        newReply.setAuthor(author);

        newReply.setContent(tweetRequestDto.getContent());
        processContent(tweetRequestDto.getContent(), newReply);
        return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(newReply));
    }

    @Override
    public TweetResponseDto createRepostOfTweet(Long id, CredentialsDto credentialsDto) {
        validateUser(credentialsDto);

        Tweet newRepost = new Tweet();

        Tweet ogTweet = getTweet(id);
        newRepost.setRepostOf(ogTweet);

        newRepost.setDeleted(false);

        User author = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername()).get();
        newRepost.setAuthor(author);

        return tweetMapper.entityToResponseDto(tweetRepository.saveAndFlush(newRepost));
    }

    @Override
    public List<HashtagDto> getTagsFromTweet(Long id) {
        Tweet tweet = getTweet(id);
        List<Hashtag> hashtags = tweet.getHashtags();
        List<HashtagDto> hashtagDtos = new ArrayList<>();
        for (Hashtag h : hashtags) {
            hashtagDtos.add(hashtagMapper.entityToDto(h));
        }
        return hashtagDtos;
    }

    @Override
    public List<UserResponseDto> getLikesFromTweet(Long id) {
        Tweet tweet = getTweet(id);
        List<User> likes = tweet.getLikes();
        List<UserResponseDto> likesDto = new ArrayList<>();
        for (User user : likes) {
            likesDto.add(userMapper.entityToDto(user));
        }
        return likesDto;
    }

    private List<TweetResponseDto> buildAfter(Tweet tweet) {

        List<TweetResponseDto> after = new ArrayList<>();
        List<Tweet> replies = tweetRepository.findRepliesByTweetId(tweet.getId());
        if (replies.isEmpty()) {
            return after;
        }

        for (Tweet reply : replies) {
            if (reply.isDeleted()) {
                continue;
            }
            after.add(tweetMapper.entityToResponseDto(reply));
            after.addAll(buildAfter(tweetRepository.findByIdAndDeletedFalse(reply.getId()).get()));
        }
        return after;
    }

    @Override
    public ContextDto getContextFromTweet(Long id) {
        Tweet tweet = getTweet(id);
        ContextDto contextDto = new ContextDto();
        contextDto.setTarget(tweetMapper.entityToResponseDto(tweet));

        List<TweetResponseDto> before = new ArrayList<>();
        Tweet current = tweet.getInReplyTo();
        while (current != null) {
            if (current.isDeleted()) {
                break;
            }
            before.add(tweetMapper.entityToResponseDto(current));
            current = current.getInReplyTo();
        }
        contextDto.setBefore(before);

        List<TweetResponseDto> after = buildAfter(tweet);

        contextDto.setAfter(after);

        return contextDto;
    }

    @Override
    public List<TweetResponseDto> getRepliesOfTweet(Long id) {
        return tweetMapper.entitiesToDtos(tweetRepository.findRepliesByTweetId(id));
    }

    @Override
    public List<TweetResponseDto> getRepostsOfTweet(Long id) {
        return tweetMapper.entitiesToDtos(tweetRepository.findRespostsByTweetId(id));
    }

    @Override
    public List<UserResponseDto> getMentionsOfTweet(Long id) {
        Tweet tweet = getTweet(id);
        List<User> mentions = tweet.getMentions();
        List<UserResponseDto> userMentions = new ArrayList<>();
        for (User user : mentions) {
            userMentions.add(userMapper.entityToDto(user));
        }
        return userMentions;
    }
}
