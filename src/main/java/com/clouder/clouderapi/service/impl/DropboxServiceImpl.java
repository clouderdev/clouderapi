package com.clouder.clouderapi.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.exception.CloudException;
import com.clouder.clouderapi.exception.ClouderException;
import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.pojo.DropBox;
import com.clouder.clouderapi.service.CloudService;
import com.clouder.clouderapi.service.UserService;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxSessionStore;
import com.dropbox.core.DbxStandardSessionStore;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.DbxWebAuth.BadRequestException;
import com.dropbox.core.DbxWebAuth.BadStateException;
import com.dropbox.core.DbxWebAuth.CsrfException;
import com.dropbox.core.DbxWebAuth.NotApprovedException;
import com.dropbox.core.DbxWebAuth.ProviderException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;

@Service("dropbox")
public class DropboxServiceImpl implements CloudService {

    @Autowired
    DbxAppInfo dbxAppInfo;

    @Autowired
    DbxRequestConfig dbxRequestConfig;

    @Value("${dropbox.redirectUri}")
    String dbxRedirectUri;

    @Autowired
    UserService userService;

    @Override
    public Cloud addCloud(HttpServletRequest servletRequest, String username) {
        @SuppressWarnings("unchecked")
        Map<String, String[]> map = servletRequest.getParameterMap();
        Cloud dropbox;
        HttpSession session = servletRequest.getSession();
        DbxSessionStore dbxSessionStore = (DbxSessionStore) session.getAttribute("dbxSessionStore");
        DbxWebAuth dbxWebAuth = new DbxWebAuth(dbxRequestConfig, dbxAppInfo, dbxRedirectUri, dbxSessionStore);
        try {
            DbxAuthFinish authFinish = dbxWebAuth.finish(map);
            String accessToken = authFinish.getAccessToken();
            User user = userService.findByUsername(username);
            DbxClientV2 dbxClient = new DbxClientV2(dbxRequestConfig, accessToken);
            String email = dbxClient.users().getCurrentAccount().getEmail();
            dropbox = new DropBox(email, accessToken, null);
            user.addCloud(dropbox);
            userService.saveUser(user);
        } catch (BadRequestException | BadStateException | CsrfException | NotApprovedException | ProviderException
                | DbxException e) {
            throw new CloudException("Could not generate access token from code", e);
        }
        return dropbox;
    }

    @Override
    public URI authenticateCloud(HttpServletRequest servletRequest, String username) {
        URI authURI = null;
        HttpSession session = servletRequest.getSession();
        String key = "dbxAuthCsrfToken";
        DbxSessionStore dbxSessionStore = new DbxStandardSessionStore(session, key);
        DbxWebAuth dbxWebAuth = new DbxWebAuth(dbxRequestConfig, dbxAppInfo, dbxRedirectUri, dbxSessionStore);
        session.setAttribute("dbxSessionStore", dbxSessionStore);
        try {
            String authURL = dbxWebAuth.start();
            System.out.println(authURL);
            authURI = new URI(authURL);
        } catch (URISyntaxException e) {
            throw new CloudException("Bad URL generated", e);
        }
        return authURI;
    }

    @Override
    public void listFiles(HttpServletRequest servletRequest, String username) {
        @SuppressWarnings("unchecked")
        Map<String, String> parameterMap = servletRequest.getParameterMap();
        String cloudId = parameterMap.get("cloudId");
        String parentDir = parameterMap.get("parentDir");
        User user = userService.findByUsername(username);
        DropBox dropbox = (DropBox) user.getCloud(cloudId);
        if (dropbox == null) {
            throw new ClouderException("No such cloud exists", null);
        }
        DbxClientV2 dbxClient = new DbxClientV2(dbxRequestConfig, dropbox.getDropBoxAccessToken());
        try {
            List<Metadata> entries = dbxClient.files().listFolder(parentDir).getEntries();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

}
