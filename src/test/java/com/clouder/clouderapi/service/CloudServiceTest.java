package com.clouder.clouderapi.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CloudServiceTest {

    @Context
    HttpServletRequest servletRequest;

    @Qualifier("onedrive")
    @Autowired
    CloudService oneDriveService;

    @Qualifier("dropbox")
    @Autowired
    CloudService dropboxService;

    @Test
    public void testAddCloud() {
        oneDriveService.addCloud(servletRequest, "shrinivas93");
        dropboxService.addCloud(servletRequest, "shrinivas93");
    }

}
