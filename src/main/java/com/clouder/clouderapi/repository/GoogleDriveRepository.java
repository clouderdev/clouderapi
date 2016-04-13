package com.clouder.clouderapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.clouder.clouderapi.document.GoogleDrive;

public interface GoogleDriveRepository extends MongoRepository<GoogleDrive, String> {

}
