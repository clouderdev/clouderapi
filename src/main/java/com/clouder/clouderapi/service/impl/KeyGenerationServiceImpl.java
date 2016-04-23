package com.clouder.clouderapi.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.dto.PublicKeyDTO;
import com.clouder.clouderapi.exception.ClouderException;
import com.clouder.clouderapi.exception.NoSuchUserException;
import com.clouder.clouderapi.service.KeyGenerationService;
import com.clouder.clouderapi.service.UserService;
import com.clouder.clouderapi.util.CryptUtility;

@Service
public class KeyGenerationServiceImpl implements KeyGenerationService {

    @Autowired
    private SecureRandom secureRandom;

    @Autowired
    private KeyPairGenerator keyPairGenerator;

    @Autowired
    private KeyFactory keyFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private CryptUtility cryptUtility;

    private String getBase64PrivateKey(KeyPair keyPair) {
        return getBase64(keyPair.getPrivate().getEncoded());
    }

    private String getBase64PublicKey(KeyPair keyPair) {
        return getBase64(keyPair.getPublic().getEncoded());
    }

    private String getBase64(byte[] key) {
        return new String(Base64.encodeBase64(key));
    }

    @Override
    public PublicKeyDTO getPublicKey(String username) throws InvalidKeySpecException {
        String publicKeyBase64;
        User user = userService.findByUsername(username);
        if (user != null) {
            publicKeyBase64 = user.getPublicKey();
            if (publicKeyBase64 == null) {
                KeyPair keyPair = cryptUtility.generateKeypair(keyPairGenerator, 512, secureRandom);
                String privateKeyBase64 = getBase64PrivateKey(keyPair);
                publicKeyBase64 = getBase64PublicKey(keyPair);
                user.setPublicKey(publicKeyBase64);
                user.setPrivateKey(privateKeyBase64);
                userService.saveUser(user);
            }
            PublicKey publicKey;
            publicKey = getPublicKeyFromBase64(publicKeyBase64);
            return getPublicKeyDTO(publicKey);
        } else {
            throw new NoSuchUserException("User with username '" + username + "' does not exists", null);
        }
    }

    private PublicKeyDTO getPublicKeyDTO(PublicKey publicKey) {
        String e = cryptUtility.getPublicKeyExponent(publicKey);
        String n = cryptUtility.getPublicKeyModulus(publicKey);
        String maxdigits = String.valueOf(cryptUtility.getMaxDigits(512));
        return new PublicKeyDTO(e, n, maxdigits);
    }

    private PublicKey getPublicKeyFromBase64(String publicKeyBase64) throws InvalidKeySpecException {
        byte[] decodeBase64 = Base64.decodeBase64(publicKeyBase64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodeBase64);
        return keyFactory.generatePublic(keySpec);
    }

    @Override
    public PrivateKey getPrivateKeyFromBase64(String privateKeyBase64) {
        try {
            byte[] decodeBase64 = Base64.decodeBase64(privateKeyBase64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeBase64);
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new ClouderException(e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String string, PrivateKey privatekey) {
        return cryptUtility.decrypt(string, privatekey);
    }

    @Override
    public String encodeString(String password) {
        return getBase64(password.getBytes(StandardCharsets.UTF_8));
    }

}
