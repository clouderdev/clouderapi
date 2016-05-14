package com.clouder.clouderapi.service;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.clouder.clouderapi.pojo.Cloud;
import com.dropbox.core.v2.files.Metadata;

public interface CloudService {

    Cloud addCloud(HttpServletRequest servletRequest, String username);

    URI authenticateCloud(HttpServletRequest servletRequest, String username);

    List<Metadata> listFiles(HttpServletRequest servletRequest, String username, String cloudId, String parentDir);

    Metadata deleteFile(HttpServletRequest servletRequest, String username, String cloudId, String filePath);

    InputStream downloadFile(HttpServletRequest servletRequest, String username, String cloudId, String filePath);

}
