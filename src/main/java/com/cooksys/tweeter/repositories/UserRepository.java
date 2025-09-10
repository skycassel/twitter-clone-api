package com.cooksys.tweeter.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.tweeter.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByCredentialsUsername(String username);

	List<User> findAllByDeletedFalse();

	Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

	
}