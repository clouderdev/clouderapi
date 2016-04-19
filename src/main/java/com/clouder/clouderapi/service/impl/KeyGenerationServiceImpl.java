package com.clouder.clouderapi.service.impl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.exception.NoSuchUserException;
import com.clouder.clouderapi.service.KeyGenerationService;
import com.clouder.clouderapi.service.UserService;

@Service
public class KeyGenerationServiceImpl implements KeyGenerationService {

    @Autowired
    private SecureRandom     secureRandom;

    @Autowired
    private KeyPairGenerator keyPairGenerator;

    @Autowired
    private UserService      userService;

    private KeyPair getKeyPair() throws NoSuchAlgorithmException {
        keyPairGenerator.initialize(512, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    private String getPrivateKey(KeyPair keyPair) {
        return getKeyString(keyPair.getPrivate().getEncoded());
    }

    private String getPublicKey(KeyPair keyPair) {
        return getKeyString(keyPair.getPublic().getEncoded());
    }

    private String getKeyString(byte[] key) {
        StringBuilder keyString = new StringBuilder();
        for (int i = 0; i < key.length; ++i) {
            keyString.append(Integer.toHexString(0x0100 + (key[i] & 0x00FF)).substring(1));
        }
        return keyString.toString();
    }

    @Override
    public String getPublicKey(String username) throws NoSuchAlgorithmException {
        String publicKey;
        User user = userService.findByUsername(username);
        if (user != null) {
            publicKey = user.getPublicKey();
            if (publicKey == null) {
                KeyPair keyPair = getKeyPair();
                String privateKey = getPrivateKey(keyPair);
                publicKey = getPublicKey(keyPair);
                user.setPublicKey(publicKey);
                user.setPrivateKey(privateKey);
                userService.saveUser(user);
            }
        } else {
            throw new NoSuchUserException("User with username '" + username + "' does not exists", null);
        }
        return publicKey;
    }

}
