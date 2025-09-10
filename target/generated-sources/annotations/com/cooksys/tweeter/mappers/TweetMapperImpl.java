package com.cooksys.tweeter.mappers;

import com.cooksys.tweeter.dtos.TweetRequestDto;
import com.cooksys.tweeter.dtos.TweetResponseDto;
import com.cooksys.tweeter.entities.Tweet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T11:45:44-0400",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class TweetMapperImpl implements TweetMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<TweetResponseDto> entitiesToDtos(List<Tweet> tweet) {
        if ( tweet == null ) {
            return null;
        }

        List<TweetResponseDto> list = new ArrayList<TweetResponseDto>( tweet.size() );
        for ( Tweet tweet1 : tweet ) {
            list.add( entityToResponseDto( tweet1 ) );
        }

        return list;
    }

    @Override
    public Tweet tweetRequestToEntity(TweetRequestDto tweetRequestDto) {
        if ( tweetRequestDto == null ) {
            return null;
        }

        Tweet tweet = new Tweet();

        tweet.setContent( tweetRequestDto.getContent() );

        return tweet;
    }

    @Override
    public TweetResponseDto entityToResponseDto(Tweet tweet) {
        if ( tweet == null ) {
            return null;
        }

        TweetResponseDto tweetResponseDto = new TweetResponseDto();

        tweetResponseDto.setAuthor( userMapper.entityToDto( tweet.getAuthor() ) );
        tweetResponseDto.setContent( tweet.getContent() );
        tweetResponseDto.setId( tweet.getId() );
        tweetResponseDto.setInReplyTo( entityToResponseDto( tweet.getInReplyTo() ) );
        tweetResponseDto.setPosted( tweet.getPosted() );
        tweetResponseDto.setRepostOf( entityToResponseDto( tweet.getRepostOf() ) );

        return tweetResponseDto;
    }
}
