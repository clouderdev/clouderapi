package com.clouder.clouderapi.service.impl;

import java.security.NoSuchAlgorithmException;

import org.junit.Ignore;
import org.junit.Test;

import com.clouder.clouderapi.service.KeyGenerationService;

public class KeyGenerationServiceImplTest {

	@Ignore
	public final void testGetKeyPair() throws NoSuchAlgorithmException {
		KeyGenerationService keyGenerationService = new KeyGenerationServiceImpl();
		//keyGenerationService.getKeyPair();
	}

	@Ignore
	public final void testGetPrivateKey() throws NoSuchAlgorithmException {
		KeyGenerationService keyGenerationService = new KeyGenerationServiceImpl();
		//KeyPair keyPair = keyGenerationService.getKeyPair();
		//String privateKey = keyGenerationService.getPrivateKey(keyPair);
		//System.out.println(privateKey);
	}
	
	@Test
	public final void testGetPublicKey() throws NoSuchAlgorithmException {
		KeyGenerationService keyGenerationService = new KeyGenerationServiceImpl();
		String key = keyGenerationService.getPublicKey("shrinivas93");
		System.out.println(key);
	}

}
