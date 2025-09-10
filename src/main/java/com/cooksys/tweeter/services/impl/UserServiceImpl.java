package com.cooksys.tweeter.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.tweeter.dtos.CreateUserRequestDto;
import com.cooksys.tweeter.dtos.CredentialsDto;
import com.cooksys.tweeter.dtos.TweetResponseDto;
import com.cooksys.tweeter.dtos.UserRequestDto;
import com.cooksys.tweeter.dtos.UserResponseDto;
import com.cooksys.tweeter.embeddables.Credentials;
import com.cooksys.tweeter.embeddables.Profile;
import com.cooksys.tweeter.entities.Tweet;
import com.cooksys.tweeter.entities.User;
import com.cooksys.tweeter.exceptions.BadRequestException;
import com.cooksys.tweeter.exceptions.NotAuthorizedException;
import com.cooksys.tweeter.exceptions.NotFoundException;
import com.cooksys.tweeter.mappers.CredentialsMapper;
import com.cooksys.tweeter.mappers.ProfileMapper;
import com.cooksys.tweeter.mappers.TweetMapper;
import com.cooksys.tweeter.mappers.UserMapper;
import com.cooksys.tweeter.repositories.UserRepository;
import com.cooksys.tweeter.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CredentialsMapper credentialsMapper;
	private final ProfileMapper profileMapper;
	private final TweetMapper tweetMapper;

	private User getUser(String username) {
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("User with username: " + username + " was not found.");
		}
		return optionalUser.get();
	}

	private void authenticateCredentials(User user, Credentials credentials) {
		if (credentials.getUsername() == null || credentials.getUsername().isEmpty()) {
			throw new BadRequestException("Username is required");
		}
		if (!user.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Username or password is incorrect.");
		}
	}

	private void validateProfile(Profile profile) {
		if (profile == null || profile.getEmail() == null || profile.getEmail().isBlank()) {
			throw new BadRequestException("Email cannot be blank");
		}
	}

	private void validateUniqueUsername(String username) {
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (optionalUser.isPresent()) {
			throw new BadRequestException("User already exists with that username.");
		}

		if (username == null || username.isEmpty()) {
			throw new BadRequestException("A username is required");
		}
	}

	private void validatePasswordPresence(String password) {
		if (password == null || password.isEmpty()) {
			throw new BadRequestException("A password is required");
		}
	}

	private void updateProfile(User user, Profile profile) {
		if (profile.getEmail() != null && !profile.getEmail().isEmpty()) {
			user.getProfile().setEmail(profile.getEmail());
		}

		if (profile.getPhone() != null && !profile.getPhone().isEmpty()) {
			user.getProfile().setPhone(profile.getPhone());
		}

		if (profile.getFirstName() != null && !profile.getFirstName().isEmpty()) {
			user.getProfile().setFirstName(profile.getFirstName());
		}

		if (profile.getLastName() != null && !profile.getLastName().isEmpty()) {
			user.getProfile().setLastName(profile.getLastName());
		}
	}

	private void validateCredentials(Credentials credentials) {
		if (credentials == null) {
			throw new BadRequestException("Username and Password are required");
		}
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
	}

	@Override
	public UserResponseDto getUserByUsername(String username) {
		User user = getUser(username);
		return userMapper.entityToDto(user);
	}

	@Override
	public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {
		User user = getUser(username);

		Credentials credentials = credentialsMapper.dtoToEntity(userRequestDto.getCredentials());
		Profile profile = profileMapper.dtoToEntity(userRequestDto.getProfile());
		validateCredentials(credentials);
		authenticateCredentials(user, credentials);
		if (profile == null) {
			throw new BadRequestException("A profile is needed");
		}

		updateProfile(user, profile);

		return userMapper.entityToDto(userRepository.saveAndFlush(user));
	}

	@Override
	public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {
		User user = getUser(username);
		Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
		authenticateCredentials(user, credentials);

		user.setDeleted(true);
		return userMapper.entityToDto(userRepository.saveAndFlush(user));
	}

	@Override
	public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
		User userToSave = userMapper.dtoToEntity(createUserRequestDto);
		Credentials credentials = userToSave.getCredentials();
		validateCredentials(credentials);
		validateUniqueUsername(credentials.getUsername());
		validatePasswordPresence(credentials.getPassword());
		validateProfile(userToSave.getProfile());
		
		User possibleDeletedUser = userRepository.findByCredentialsUsername(credentials.getUsername());
		if(possibleDeletedUser != null) {
			possibleDeletedUser.setDeleted(false);
			return userMapper.entityToDto(userRepository.saveAndFlush(possibleDeletedUser));
		}

		return userMapper.entityToDto(userRepository.saveAndFlush(userToSave));
	}

	@Override
	public void followUser(String username, CredentialsDto credentialsDto) {
		User followedUser = getUser(username);

		// If user can't be followed, throw error
		if (followedUser.isDeleted()) {
			throw new BadRequestException("Cannot follow a deleted account.");
		}

		Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
		validateCredentials(credentials);
		validatePasswordPresence(credentials.getPassword());
		User followingUser = getUser(credentials.getUsername());

		if (followingUser.isDeleted()) {
			throw new BadRequestException("Must provide credentials for an active user.");
		}
		
		// If already following, send error
		if(followedUser.getFollowers().contains(followingUser)) {
			throw new BadRequestException("Following relationship doesn't exist between these users.");
		}
		
		followingUser.getFollowing().add(followedUser);

		userRepository.saveAndFlush(followingUser);
	}
	
	@Override
	public void unfollowUser(String username, CredentialsDto credentialsDto) {
		User unfollowedUser = getUser(username);

		// If user can't be followed, throw error
		if (unfollowedUser.isDeleted()) {
			throw new BadRequestException("Cannot unfollow a deleted account.");
		}

		Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
		validateCredentials(credentials);
		validatePasswordPresence(credentials.getPassword());
		User unfollowingUser = getUser(credentials.getUsername());

		if (unfollowingUser.isDeleted()) {
			throw new BadRequestException("Must provide credentials for an active user.");
		}

		// If no following relationship to change, send error
		if(!unfollowedUser.getFollowers().contains(unfollowingUser)) {
			throw new BadRequestException("Following relationship doesn't exist between these users.");
		}
		
		unfollowingUser.getFollowing().remove(unfollowedUser);

		userRepository.saveAndFlush(unfollowingUser);
	}

	@Override
	public List<UserResponseDto> getFollowers(String username) {
		User user = getUser(username);

		// If user can't be followed, throw error
		if (user.isDeleted()) {
			throw new BadRequestException("Given account is not active.");
		}
		
		// Gets a list of all active followers
		List<User> activeFollowers = new ArrayList<>(user.getFollowers());
		for(User follower : activeFollowers) {
			if(follower.isDeleted()) {
				activeFollowers.remove(follower);
			}
		}
		
		return userMapper.entitiesToDtos(activeFollowers);
	}

	@Override

	public List<TweetResponseDto> getFeed(String username) {
		User user = getUser(username);
		
		// Add user's tweets to feed
		List<Tweet> feed = new ArrayList<>(user.getTweets());
		
		// Add tweets of all the users that this user is following
		for (User u : user.getFollowing()) {
			feed.addAll(u.getTweets());
		}

		return tweetMapper.entitiesToDtos(feed);
	}

	@Override
	public List<TweetResponseDto> getTweets(String username) {
		User user = getUser(username);

		return tweetMapper.entitiesToDtos(user.getTweets());
  }

  @Override
	public List<UserResponseDto> getFollowing(String username) {
		User user = getUser(username);

		// If user can't be followed, throw error
		if (user.isDeleted()) {
			throw new BadRequestException("Given account is not active.");
		}
		
		// Gets a list of all active followers
		List<User> activeFollowing = new ArrayList<>(user.getFollowing());
		for(User following : activeFollowing) {
			if(following.isDeleted()) {
				activeFollowing.remove(following);
			}
		}
		
		return userMapper.entitiesToDtos(activeFollowing);
	}

	@Override
	public List<TweetResponseDto> getMentions(String username) {
		User user = getUser(username);
		return tweetMapper.entitiesToDtos(user.getTweetMentions());
	}

}
