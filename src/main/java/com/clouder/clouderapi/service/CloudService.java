package com.clouder.clouderapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.clouder.clouderapi.pojo.Cloud;
import com.dropbox.core.v2.files.Metadata;

public interface CloudService {

    Cloud addCloud(HttpServletRequest servletRequest, String username);

    URI authenticateCloud(HttpServletRequest servletRequest, String username);

    List<Metadata> listFiles(String username, String cloudId, String parentDir);

    Metadata deleteFile(String username, String cloudId, String filePath);

    InputStream downloadFile(String username, String cloudId, String filePath);

    InputStream uploadFile(String username, String cloudId, String filePath, InputStream inputStream,
            FormDataContentDisposition fileDetails) throws IOException;

}
