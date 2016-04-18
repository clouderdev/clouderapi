package com.clouder.clouderapi.service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public interface KeyGenerationService {
	KeyPair getKeyPair() throws NoSuchAlgorithmException;

	String getPrivateKey(KeyPair keyPair);

	String getPublicKey(KeyPair keyPair);
}
