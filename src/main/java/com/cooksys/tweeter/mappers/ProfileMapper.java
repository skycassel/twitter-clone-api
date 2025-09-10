package com.cooksys.tweeter.mappers;

import org.mapstruct.Mapper;

import com.cooksys.tweeter.dtos.ProfileDto;
import com.cooksys.tweeter.embeddables.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  ProfileDto entityToDto(Profile profile);

  Profile dtoToEntity(ProfileDto profileDto);
}
