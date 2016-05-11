package com.clouder.clouderapi.service.impl;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.service.CloudService;

@Service("onedrive")
public class OneDriveServiceImpl implements CloudService {

    @Override
    public Cloud addCloud(HttpServletRequest servletRequest, String username) {
        System.out.println("OneDrive" + username);
        return null;
    }

    @Override
    public URI authenticateCloud(HttpServletRequest servletRequest, String username) {
        // TODO Auto-generated method stub
        return null;
    }

}
