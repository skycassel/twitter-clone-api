package com.cooksys.tweeter.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.tweeter.dtos.HashtagDto;
import com.cooksys.tweeter.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

	HashtagDto entityToDto(Hashtag entity);
	
	List<HashtagDto> entitiesToDtos(List<Hashtag> entities);
	
	Hashtag dtoToEntity(HashtagDto hashtagDto);

}
