package com.clouder.clouderapi.util;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.clouder.clouderapi.exception.ClouderException;
import com.clouder.clouderapi.pojo.Constants;

public class CryptUtility {

    public CryptUtility() {
        java.security.Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Generates the {@link KeyPair} with the given keyLength.
     * 
     * @param keyPairGenerator
     * 
     * @param keyLength
     *            length of key
     * @return keyPair the keyPair generated
     * @throws RuntimeException
     *             if the CRYPT_ALGO algorithm not supported
     */
    public KeyPair generateKeypair(KeyPairGenerator keyPairGenerator, int keyLength, SecureRandom secureRandom) {
        keyPairGenerator.initialize(keyLength, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Decrypts a given string with the CRYPT_ALGO keys
     * 
     * @param encrypted
     *            full encrypted text
     * @param keys
     *            CRYPT_ALGO keys
     * @return decrypted text
     * @throws RuntimeException
     *             if the CRYPT_ALGO algorithm not supported or decrypt
     *             operation failed
     */
    public String decrypt(String encrypted, PrivateKey privateKey) {
        Cipher dec;
        try {
            dec = Cipher.getInstance(Constants.CRYPT_ALGO_TRANSFORMATION);
            dec.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (GeneralSecurityException e) {
            throw new ClouderException("CRYPT_ALGO algorithm not supported", e);
        }
        String[] blocks = encrypted.split("\\s");
        StringBuilder result = new StringBuilder();
        try {
            for (int i = blocks.length - 1; i >= 0; i--) {
                byte[] data = hexStringToByteArray(blocks[i]);
                byte[] decryptedBlock = dec.doFinal(data);
                result.append(new String(decryptedBlock));
            }
        } catch (GeneralSecurityException e) {
            throw new ClouderException("Decrypt error", e);
        }
        /**
         * Some code is getting added in first 2 digits with Jcryption need to
         * investigate
         */
        return result.reverse().toString().substring(2);
    }

    /**
     * Return public CRYPT_ALGO key modulus
     * 
     * @param keyPair
     *            CRYPT_ALGO keys
     * @return modulus value as hex string
     */
    public String getPublicKeyModulus(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        return rsaPublicKey.getModulus().toString(16);
    }

    /**
     * Return public CRYPT_ALGO key exponent
     * 
     * @param keyPair
     *            CRYPT_ALGO keys
     * @return public exponent value as hex string
     */
    public String getPublicKeyExponent(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        return rsaPublicKey.getPublicExponent().toString(16);
    }

    /**
     * Max block size with given key length
     * 
     * @param keyLength
     *            length of key
     * @return number of digits
     */
    public int getMaxDigits(int keyLength) {
        return ((keyLength * 2) / 16) + 3;
    }

    /**
     * Convert byte array to hex string
     * 
     * @param bytes
     *            input byte array
     * @return Hex string representation
     */
    public String byteArrayToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    /**
     * Convert hex string to byte array
     * 
     * @param data
     *            input string data
     * @return bytes
     */
    public byte[] hexStringToByteArray(String data) {
        int k = 0;
        byte[] results = new byte[data.length() / 2];
        for (int i = 0; i < data.length(); i = i + 2) {
            results[k] = (byte) (Character.digit(data.charAt(i), 16) << 4);
            results[k] += (byte) (Character.digit(data.charAt(i + 1), 16));
            k++;
        }
        return results;
    }

}