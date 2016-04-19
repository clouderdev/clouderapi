package com.clouder.clouderapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.clouder.clouderapi.document.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(@Param("username") String username);

	String findPublicKey(@Param("username") String username);
}
