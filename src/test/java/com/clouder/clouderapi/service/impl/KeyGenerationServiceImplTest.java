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
        Assert.assertEquals("Incorrect publicKey",
                "305c300d06092a864886f70d0101010500034b003048024100a1b8e40c4b77b47b55816713950b90d3cb39de1866dbd699d3a5b09fac977122a9c704832aba824c3c12cd4ab5fd26f80bd490fba7e76b3ca94fb74318ce3cf90203010001",
                publicKey);
    }

}
