package com.clouder.clouderapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.clouder.clouderapi.document.DropBox;

public interface DropBoxRepository extends MongoRepository<DropBox, String> {

}
