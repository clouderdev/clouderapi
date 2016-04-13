package com.clouder.clouderapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.clouder.clouderapi.document.User;

public interface UserRepository extends MongoRepository<User, String> {

}
