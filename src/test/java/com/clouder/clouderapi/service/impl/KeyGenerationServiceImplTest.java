package com.clouder.clouderapi.service.impl;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.clouder.clouderapi.service.KeyGenerationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class KeyGenerationServiceImplTest {

    @Autowired
    KeyGenerationService keyGenerationService;

    @Test
    public final void testGetPublicKey() throws NoSuchAlgorithmException {
        String publicKey = keyGenerationService.getPublicKey("shrinivas93");
        // Shriroop: changed the key so as to suit my database
        Assert.assertEquals("Incorrect publicKey",
                "305c300d06092a864886f70d0101010500034b0030480241008f04715bb17de74e0a8d6fcde81ae8c9a356285c6827b6c1836fbe56f468b20c567f18123d309bc4a178fddb8078739cf9a9d27da3b26ee3e16c83e17b5f9b8d0203010001",
                publicKey);
    }

}
