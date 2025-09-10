package com.cooksys.tweeter.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Profile {

  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  
}
