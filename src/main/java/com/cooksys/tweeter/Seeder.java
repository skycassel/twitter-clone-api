// package com.cooksys.tweeter;

// import com.cooksys.tweeter.embeddables.Credentials;
// import com.cooksys.tweeter.entities.Hashtag;
// import com.cooksys.tweeter.embeddables.Profile;
// import com.cooksys.tweeter.entities.Tweet;
// import com.cooksys.tweeter.entities.User;
// import com.cooksys.tweeter.repositories.HashtagRepository;
// import com.cooksys.tweeter.repositories.TweetRepository;
// import com.cooksys.tweeter.repositories.UserRepository;
// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.sql.Timestamp;
// import java.util.Arrays;

// @Component
// @RequiredArgsConstructor
// public class Seeder implements CommandLineRunner {

//     private final UserRepository userRepo;
//     private final TweetRepository tweetRepo;
//     private final HashtagRepository hashtagRepo;

//     @Override
//     @Transactional
//     public void run(String... args) throws Exception {
//         // Create Users
//         User alice = new User();
//         Credentials aliceCredentials = new Credentials();
//         Profile aliceProfile = new Profile();
//         aliceCredentials.setUsername("alice123");
//         aliceCredentials.setPassword("password");
//         aliceProfile.setFirstName("Alice");
//         aliceProfile.setLastName("Anderson");
//         aliceProfile.setEmail("alice@example.com");
//         aliceProfile.setPhone("123-456-7890");
//         alice.setJoined(new Timestamp(System.currentTimeMillis()));
//         alice.setCredentials(aliceCredentials);
//         alice.setProfile(aliceProfile);
//         alice.setDeleted(false);
//         userRepo.save(alice);

//         User bob = new User();
//         Credentials bobCredentials = new Credentials();
//         Profile bobProfile = new Profile();
//         bobCredentials.setUsername("bob456");
//         bobCredentials.setPassword("password");
//         bobProfile.setFirstName("Bob");
//         bobProfile.setLastName("Brown");
//         bobProfile.setEmail("bob@example.com");
//         bobProfile.setPhone("987-654-3210");
//         bob.setJoined(new Timestamp(System.currentTimeMillis()));
//         bob.setCredentials(bobCredentials);
//         bob.setProfile(bobProfile);
//         bob.setDeleted(false);
//         userRepo.save(bob);

//         // Create Hashtags
//         Hashtag javaTag = new Hashtag();
//         javaTag.setLabel("Java");
//         javaTag.setFirstUsed(new Timestamp(System.currentTimeMillis()));
//         javaTag.setLastUsed(new Timestamp(System.currentTimeMillis()));
//         hashtagRepo.save(javaTag);

//         Hashtag springTag = new Hashtag();
//         springTag.setLabel("SpringBoot");
//         springTag.setFirstUsed(new Timestamp(System.currentTimeMillis()));
//         springTag.setLastUsed(new Timestamp(System.currentTimeMillis()));
//         hashtagRepo.save(springTag);

//         // Create Tweet
//         Tweet tweet = new Tweet();
//         tweet.setAuthor(alice);
//         tweet.setPosted(new Timestamp(System.currentTimeMillis()));
//         tweet.setDeleted(false);
//         tweet.setContent("Hello world!");
//         tweet.setHashtags(Arrays.asList(javaTag, springTag));
//         tweet.setLikes(Arrays.asList(bob));
//         tweet.setMentions(Arrays.asList(bob));
//         tweetRepo.save(tweet);
//     }
// }
