package com.clouder.clouderapi.service;

import java.security.NoSuchAlgorithmException;

@FunctionalInterface
public interface KeyGenerationService {
    String getPublicKey(String username) throws NoSuchAlgorithmException;
}
