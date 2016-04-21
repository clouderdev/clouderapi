package com.clouder.clouderapi.service;

import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import com.clouder.clouderapi.dto.PublicKeyDTO;

public interface KeyGenerationService {
    PublicKeyDTO getPublicKey(String username) throws InvalidKeySpecException;

    PrivateKey getPrivateKeyFromBase64(String privateKeyBase64);

    String decrypt(String password, PrivateKey privatekey);

    String hash(String password);
}
