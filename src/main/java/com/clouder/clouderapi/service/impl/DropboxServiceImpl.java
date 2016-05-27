package com.clouder.clouderapi.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.exception.CloudException;
import com.clouder.clouderapi.exception.ClouderException;
import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.pojo.Constants;
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
import com.dropbox.core.v2.files.CommitInfo;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.DownloadErrorException;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadSessionCursor;
import com.dropbox.core.v2.files.UploadSessionFinishUploader;
import com.dropbox.core.v2.files.UploadSessionStartResult;
import com.dropbox.core.v2.files.UploadSessionStartUploader;
import com.dropbox.core.v2.files.WriteMode;

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
            authURI = new URI(authURL);
        } catch (URISyntaxException e) {
            throw new CloudException("Bad URL generated", e);
        }
        return authURI;
    }

    @Override
    public List<Metadata> listFiles(String username, String cloudId, String parentDir) {
        User user = userService.findByUsername(username);
        DropBox dropbox = (DropBox) user.getCloud(cloudId);
        if (dropbox == null) {
            throw new ClouderException(Constants.NO_SUCH_CLOUD_EXIST, null);
        }
        DbxClientV2 dbxClient = new DbxClientV2(dbxRequestConfig, dropbox.getDropBoxAccessToken());
        try {
            return dbxClient.files().listFolderBuilder(parentDir).withRecursive(false).start().getEntries();
        } catch (DbxException e) {
            throw new ClouderException("Error occured in retrieving files", e);
        }
    }

    @Override
    public Metadata deleteFile(String username, String cloudId, String filePath) {
        User user = userService.findByUsername(username);
        DropBox dropbox = (DropBox) user.getCloud(cloudId);
        if (dropbox == null) {
            throw new ClouderException(Constants.NO_SUCH_CLOUD_EXIST, null);
        }
        DbxClientV2 dbxClient = new DbxClientV2(dbxRequestConfig, dropbox.getDropBoxAccessToken());
        try {
            return dbxClient.files().delete(filePath);
        } catch (DeleteErrorException e) {
            throw new ClouderException("Unable to delete the file specified", e);
        } catch (DbxException e) {
            throw new ClouderException("Some error occured during file delete", e);
        }
    }

    @Override
    public InputStream downloadFile(String username, String cloudId, String filePath) {
        User user = userService.findByUsername(username);
        DropBox dropbox = (DropBox) user.getCloud(cloudId);
        if (dropbox == null) {
            throw new ClouderException(Constants.NO_SUCH_CLOUD_EXIST, null);
        }
        DbxClientV2 dbxClient = new DbxClientV2(dbxRequestConfig, dropbox.getDropBoxAccessToken());
        try {
            return dbxClient.files().download(filePath).getInputStream();
        } catch (DownloadErrorException e) {
            throw new ClouderException("Unable to download the file specified", e);
        } catch (DbxException e) {
            throw new ClouderException("Some error occured during file download", e);
        }
    }

    @Override
    public void uploadFile(String username, String cloudId, String filePath, InputStream inputStream,
            FormDataContentDisposition fileDetails) {
        User user = userService.findByUsername(username);
        DropBox dropbox = (DropBox) user.getCloud(cloudId);
        if (dropbox == null) {
            throw new ClouderException(Constants.NO_SUCH_CLOUD_EXIST, null);
        }
        DbxClientV2 dbxClient = new DbxClientV2(dbxRequestConfig, dropbox.getDropBoxAccessToken());
        UploadSessionFinishUploader uploadSessionFinishUploader = null;
        try {
            DbxUserFilesRequests filesRequests = dbxClient.files();
            UploadSessionStartUploader uploadSessionStartUploader = filesRequests.uploadSessionStart();
            UploadSessionStartResult uploadSessionStartResult = uploadSessionStartUploader.uploadAndFinish(inputStream,
                    Constants.DBX_UPLOAD_LIMIT);
            String sessionId = uploadSessionStartResult.getSessionId();
            UploadSessionCursor uploadSessionCursor;
            byte[] data = new byte[Constants.DBX_UPLOAD_LIMIT];
            int bytesRead = inputStream.read(data);
            long totalBytesRead = 0;
            int prevBytesRead = 0;
            while (bytesRead != -1) {
                prevBytesRead = bytesRead;
                totalBytesRead = totalBytesRead + bytesRead;
                uploadSessionCursor = new UploadSessionCursor(sessionId, totalBytesRead);
                filesRequests.uploadSessionAppendV2(uploadSessionCursor).uploadAndFinish(inputStream,
                        Constants.DBX_UPLOAD_LIMIT);
                bytesRead = inputStream.read(data);
            }
            CommitInfo commitInfo = new CommitInfo(filePath + "/" + fileDetails.getName(), WriteMode.ADD, true,
                    Calendar.getInstance().getTime(), false);
            totalBytesRead = totalBytesRead + prevBytesRead;
            uploadSessionCursor = new UploadSessionCursor(sessionId, totalBytesRead);
            uploadSessionFinishUploader = filesRequests.uploadSessionFinish(uploadSessionCursor, commitInfo);
            uploadSessionFinishUploader.finish();
        } catch (DbxException e) {
            throw new ClouderException("Unable to start Upload Session", e);
        } catch (IOException e) {
            throw new ClouderException("Unable to read the inputstream", e);
        } finally {
            if (uploadSessionFinishUploader != null) {
                uploadSessionFinishUploader.close();
            }
        }
    }
}
