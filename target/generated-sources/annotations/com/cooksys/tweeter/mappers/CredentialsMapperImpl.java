package com.cooksys.tweeter.mappers;

import com.cooksys.tweeter.dtos.CredentialsDto;
import com.cooksys.tweeter.embeddables.Credentials;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T11:45:44-0400",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CredentialsMapperImpl implements CredentialsMapper {

    @Override
    public CredentialsDto entityToDto(Credentials credentials) {
        if ( credentials == null ) {
            return null;
        }

        CredentialsDto credentialsDto = new CredentialsDto();

        credentialsDto.setPassword( credentials.getPassword() );
        credentialsDto.setUsername( credentials.getUsername() );

        return credentialsDto;
    }

    @Override
    public Credentials dtoToEntity(CredentialsDto credentialsDto) {
        if ( credentialsDto == null ) {
            return null;
        }

        Credentials credentials = new Credentials();

        credentials.setPassword( credentialsDto.getPassword() );
        credentials.setUsername( credentialsDto.getUsername() );

        return credentials;
    }
}
