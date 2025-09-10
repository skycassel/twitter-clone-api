package com.cooksys.tweeter.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import com.cooksys.tweeter.embeddables.Credentials;
import com.cooksys.tweeter.embeddables.Profile;

@Table(name = "user_table")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @CreationTimestamp
  private Timestamp joined;

  private boolean deleted = false;

  @Embedded
  private Profile profile;

  @Embedded
  private Credentials credentials;

  @ManyToMany
  @JoinTable(name = "followers_following", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "following_id"))
  private List<User> following;

  @ManyToMany(mappedBy = "following")
  private List<User> followers;

  // We do need this because this allows us to run user.getTweets() in our
  // service instead of having to query the tweets repository with findAllByUserId(id).
  @OneToMany(mappedBy = "author")
  @SQLRestriction("deleted = false")
  @OrderBy("posted DESC")
  private List<Tweet> tweets;

  @ManyToMany(mappedBy = "mentions")
  @SQLRestriction("deleted = false")
  @OrderBy("posted DESC")
  private List<Tweet> tweetMentions;
}
