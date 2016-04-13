package com.clouder.clouderapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.clouder.clouderapi.document.Cloud;

public interface CloudRepository extends MongoRepository<Cloud, String> {

}
