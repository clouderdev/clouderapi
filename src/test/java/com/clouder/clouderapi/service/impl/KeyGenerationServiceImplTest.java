package com.clouder.clouderapi.service.impl;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.clouder.clouderapi.service.KeyGenerationService;

public class KeyGenerationServiceImplTest {

	@Test
	public final void testGetKeyPair() throws NoSuchAlgorithmException {
		KeyGenerationService keyGenerationService = new KeyGenerationServiceImpl();
		keyGenerationService.getKeyPair();
	}

	@Test
	public final void testGetPrivateKey() throws NoSuchAlgorithmException {
		KeyGenerationService keyGenerationService = new KeyGenerationServiceImpl();
		KeyPair keyPair = keyGenerationService.getKeyPair();
		String privateKey = keyGenerationService.getPrivateKey(keyPair);
		System.out.println(privateKey);
	}

	@Test
	public final void testGetPublicKey() throws NoSuchAlgorithmException {
		KeyGenerationService keyGenerationService = new KeyGenerationServiceImpl();
		KeyPair keyPair = keyGenerationService.getKeyPair();
		String publicKey = keyGenerationService.getPublicKey(keyPair);
		System.out.println(publicKey);
	}

}
