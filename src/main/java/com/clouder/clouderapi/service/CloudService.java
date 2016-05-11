package com.clouder.clouderapi.service;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import com.clouder.clouderapi.pojo.Cloud;

public interface CloudService {

    Cloud addCloud(HttpServletRequest servletRequest, String username);

    URI authenticateCloud(HttpServletRequest servletRequest, String username);

}
