package com.cooksys.tweeter.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto {

  //required field
  private String email;

  private String phone;

  private String firstName;

  private String lastName;
}
