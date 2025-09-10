package com.cooksys.tweeter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@NoArgsConstructor
//@Getter
//@Setter
@Data
public class Hashtag {

  @Id
  @GeneratedValue
  private Long id;

  private String label;

  @CreationTimestamp
  private Timestamp firstUsed;
  
  @UpdateTimestamp
  private Timestamp lastUsed;

  // This is required for the join table if we want to have the built in query/method hashtag.getTweets()
  @ManyToMany(mappedBy = "hashtags")
  @SQLRestriction("deleted = false")
  @OrderBy("posted DESC")
  private List<Tweet> tweets;
}
