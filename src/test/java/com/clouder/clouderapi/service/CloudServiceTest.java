package com.clouder.clouderapi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CloudServiceTest {

    @Qualifier("onedrive")
    @Autowired
    CloudService oneDriveService;

    @Qualifier("dropbox")
    @Autowired
    CloudService dropboxService;

    @Test
    public void testAddCloud() {
        oneDriveService.addCloud("shrinivas93", null);
        dropboxService.addCloud("shrinivas93", null);
    }

}
