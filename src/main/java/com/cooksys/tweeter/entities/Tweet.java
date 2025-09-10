package com.cooksys.tweeter.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
//@Getter
//@Setter
public class Tweet {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private User author;
  
  @CreationTimestamp
  private Timestamp posted;

  private boolean deleted;
  private String content;

  @ManyToOne
  private Tweet inReplyTo;

  @ManyToOne
  private Tweet repostOf;

  // One side has to own the join table, in the case below it could be the Hashtag
  // or
  // Tweet Entity.
  // This allows us to call tweet.getHashtags which will reach through the
  // join table to get a List of hashtag records.
  @ManyToMany
  @JoinTable(name = "tweet_hashtags", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
  private List<Hashtag> hashtags;

  @ManyToMany
  @JoinTable(name = "user_likes", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> likes;

  @ManyToMany
  @JoinTable(name = "user_mentions", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> mentions;
}
