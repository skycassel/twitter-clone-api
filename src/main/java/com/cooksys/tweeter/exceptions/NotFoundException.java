package com.cooksys.tweeter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = -9022466932646231518L;
  private String message;

}
