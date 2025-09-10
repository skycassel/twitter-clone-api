package com.cooksys.tweeter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {
  
  private static final long serialVersionUID = -3504156908486924907L;
  private String message;

}
