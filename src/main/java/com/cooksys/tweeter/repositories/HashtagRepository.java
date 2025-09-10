package com.cooksys.tweeter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.tweeter.entities.Hashtag;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
	
	Hashtag findByLabel(String label);

	Optional<Hashtag> findOptionalByLabel(String label);
}