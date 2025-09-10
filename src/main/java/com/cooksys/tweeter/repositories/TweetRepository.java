package com.cooksys.tweeter.repositories;

import com.cooksys.tweeter.dtos.TweetResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cooksys.tweeter.entities.Tweet;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>{

    List<Tweet> findAllByDeletedFalseOrderByPostedDesc();

    Optional<Tweet> findByIdAndDeletedFalse(Long id);

    @Query("SELECT t FROM Tweet t WHERE t.inReplyTo.id = :id AND deleted = false")
    List<Tweet> findRepliesByTweetId(@Param("id") Long id);

    @Query("SELECT t FROM Tweet t WHERE t.repostOf.id = :id AND deleted = false")
    List<Tweet> findRespostsByTweetId(@Param("id") Long id);
}
