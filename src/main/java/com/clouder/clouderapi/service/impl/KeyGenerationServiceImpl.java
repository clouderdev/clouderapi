package com.clouder.clouderapi.service.impl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.service.KeyGenerationService;

@Service
public class KeyGenerationServiceImpl implements KeyGenerationService {

	@Autowired
	SecureRandom secureRandom;

	@Autowired
	KeyPairGenerator keyPairGenerator;

	@Override
	public KeyPair getKeyPair() throws NoSuchAlgorithmException {
		keyPairGenerator.initialize(512, secureRandom);
		return keyPairGenerator.generateKeyPair();
	}

	@Override
	public String getPrivateKey(KeyPair keyPair) {
		return getKeyString(keyPair.getPrivate().getEncoded());
	}

	@Override
	public String getPublicKey(KeyPair keyPair) {
		return getKeyString(keyPair.getPublic().getEncoded());
	}

	private String getKeyString(byte[] key) {
		StringBuilder keyString = new StringBuilder();
		for (int i = 0; i < key.length; ++i) {
			keyString.append(Integer.toHexString(0x0100 + (key[i] & 0x00FF)).substring(1));
		}
		return keyString.toString();
	}

}
