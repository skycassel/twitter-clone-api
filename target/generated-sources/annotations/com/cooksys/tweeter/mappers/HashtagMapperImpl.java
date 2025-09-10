package com.cooksys.tweeter.mappers;

import com.cooksys.tweeter.dtos.HashtagDto;
import com.cooksys.tweeter.entities.Hashtag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T11:45:44-0400",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class HashtagMapperImpl implements HashtagMapper {

    @Override
    public HashtagDto entityToDto(Hashtag entity) {
        if ( entity == null ) {
            return null;
        }

        HashtagDto hashtagDto = new HashtagDto();

        hashtagDto.setFirstUsed( entity.getFirstUsed() );
        hashtagDto.setLabel( entity.getLabel() );
        hashtagDto.setLastUsed( entity.getLastUsed() );

        return hashtagDto;
    }

    @Override
    public List<HashtagDto> entitiesToDtos(List<Hashtag> entities) {
        if ( entities == null ) {
            return null;
        }

        List<HashtagDto> list = new ArrayList<HashtagDto>( entities.size() );
        for ( Hashtag hashtag : entities ) {
            list.add( entityToDto( hashtag ) );
        }

        return list;
    }

    @Override
    public Hashtag dtoToEntity(HashtagDto hashtagDto) {
        if ( hashtagDto == null ) {
            return null;
        }

        Hashtag hashtag = new Hashtag();

        hashtag.setFirstUsed( hashtagDto.getFirstUsed() );
        hashtag.setLabel( hashtagDto.getLabel() );
        hashtag.setLastUsed( hashtagDto.getLastUsed() );

        return hashtag;
    }
}
