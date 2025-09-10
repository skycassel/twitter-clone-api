package com.cooksys.tweeter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {

  private static final long serialVersionUID = -7198342551665889376L;
  private String message;

}
