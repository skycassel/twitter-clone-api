package com.cooksys.tweeter.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateUserRequestDto {
  private CredentialsDto credentials;
  private ProfileDto profile;
}
