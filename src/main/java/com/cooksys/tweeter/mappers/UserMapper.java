package com.cooksys.tweeter.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.tweeter.dtos.CreateUserRequestDto;
import com.cooksys.tweeter.dtos.UserRequestDto;
import com.cooksys.tweeter.dtos.UserResponseDto;
import com.cooksys.tweeter.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  
  List<UserResponseDto> entitiesToDtos(List<User> entities);
  
  @Mapping(target = "username", source = "credentials.username" )
  UserResponseDto entityToDto(User entity);

  User dtoToEntity(UserRequestDto userRequestDto);

  User dtoToEntity(CreateUserRequestDto createUserRequestDto);
  

}
