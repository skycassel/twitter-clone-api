package com.cooksys.tweeter.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetResponseDto {

  private Long id;

  private UserResponseDto author;

  private Timestamp posted;


  // Are not required
  private String content;
  
  private TweetResponseDto inReplyTo;

  private TweetResponseDto repostOf;

}
