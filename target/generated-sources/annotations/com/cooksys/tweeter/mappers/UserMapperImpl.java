package com.cooksys.tweeter.mappers;

import com.cooksys.tweeter.dtos.CreateUserRequestDto;
import com.cooksys.tweeter.dtos.CredentialsDto;
import com.cooksys.tweeter.dtos.ProfileDto;
import com.cooksys.tweeter.dtos.UserRequestDto;
import com.cooksys.tweeter.dtos.UserResponseDto;
import com.cooksys.tweeter.embeddables.Credentials;
import com.cooksys.tweeter.embeddables.Profile;
import com.cooksys.tweeter.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T11:45:45-0400",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public List<UserResponseDto> entitiesToDtos(List<User> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( entities.size() );
        for ( User user : entities ) {
            list.add( entityToDto( user ) );
        }

        return list;
    }

    @Override
    public UserResponseDto entityToDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUsername( entityCredentialsUsername( entity ) );
        userResponseDto.setJoined( entity.getJoined() );
        userResponseDto.setProfile( profileToProfileDto( entity.getProfile() ) );

        return userResponseDto;
    }

    @Override
    public User dtoToEntity(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setCredentials( credentialsDtoToCredentials( userRequestDto.getCredentials() ) );
        user.setProfile( profileDtoToProfile( userRequestDto.getProfile() ) );

        return user;
    }

    @Override
    public User dtoToEntity(CreateUserRequestDto createUserRequestDto) {
        if ( createUserRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setCredentials( credentialsDtoToCredentials( createUserRequestDto.getCredentials() ) );
        user.setProfile( profileDtoToProfile( createUserRequestDto.getProfile() ) );

        return user;
    }

    private String entityCredentialsUsername(User user) {
        Credentials credentials = user.getCredentials();
        if ( credentials == null ) {
            return null;
        }
        return credentials.getUsername();
    }

    protected ProfileDto profileToProfileDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setEmail( profile.getEmail() );
        profileDto.setFirstName( profile.getFirstName() );
        profileDto.setLastName( profile.getLastName() );
        profileDto.setPhone( profile.getPhone() );

        return profileDto;
    }

    protected Credentials credentialsDtoToCredentials(CredentialsDto credentialsDto) {
        if ( credentialsDto == null ) {
            return null;
        }

        Credentials credentials = new Credentials();

        credentials.setPassword( credentialsDto.getPassword() );
        credentials.setUsername( credentialsDto.getUsername() );

        return credentials;
    }

    protected Profile profileDtoToProfile(ProfileDto profileDto) {
        if ( profileDto == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setEmail( profileDto.getEmail() );
        profile.setFirstName( profileDto.getFirstName() );
        profile.setLastName( profileDto.getLastName() );
        profile.setPhone( profileDto.getPhone() );

        return profile;
    }
}
