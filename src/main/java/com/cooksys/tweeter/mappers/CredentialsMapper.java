package com.cooksys.tweeter.mappers;

import org.mapstruct.Mapper;

import com.cooksys.tweeter.dtos.CredentialsDto;
import com.cooksys.tweeter.embeddables.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

  CredentialsDto entityToDto(Credentials credentials);

  Credentials dtoToEntity(CredentialsDto credentialsDto);

}
