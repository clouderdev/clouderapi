package com.clouder.clouderapi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.exception.CloudException;
import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.pojo.DropBox;
import com.clouder.clouderapi.service.CloudService;
import com.clouder.clouderapi.service.UserService;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.DbxWebAuth.BadRequestException;
import com.dropbox.core.DbxWebAuth.BadStateException;
import com.dropbox.core.DbxWebAuth.CsrfException;
import com.dropbox.core.DbxWebAuth.NotApprovedException;
import com.dropbox.core.DbxWebAuth.ProviderException;

@Service("dropbox")
public class DropboxServiceImpl implements CloudService {

    @Autowired
    DbxWebAuth dbxWebAuth;

    @Autowired
    UserService userService;

    @Override
    public Cloud addCloud(String username, String code) {
        Map<String, String[]> map = new HashMap<>();
        map.put("code", new String[] { code });
        Cloud dropbox;
        try {
            String start = dbxWebAuth.start();
            System.out.println(start);
            DbxAuthFinish authFinish = dbxWebAuth.finish(map);
            String accessToken = authFinish.getAccessToken();
            User user = userService.findByUsername(username);
            dropbox = new DropBox(accessToken, null);
            List<Cloud> userClouds = user.getClouds();
            userClouds.add(dropbox);
            user.setClouds(userClouds);
            userService.saveUser(user);
        } catch (BadRequestException | BadStateException | CsrfException | NotApprovedException | ProviderException
                | DbxException e) {
            throw new CloudException("Could not generate access token from code", e);
        }
        return dropbox;
    }

}
