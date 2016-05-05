package com.clouder.clouderapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.service.CloudService;
import com.clouder.clouderapi.service.UserService;

@Service
public class OneDriveServiceImpl implements CloudService {

    @Autowired
    private UserService userService;

    @Override
    public Cloud addCloud(String username, String... codes) {
        // TODO Auto-generated method stub
        return null;
    }

}
