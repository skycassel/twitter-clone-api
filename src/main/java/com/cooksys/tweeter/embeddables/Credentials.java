package com.cooksys.tweeter.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Credentials {

  private String username;
  private String password;

}
