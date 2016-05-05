package com.clouder.clouderapi.service;

import com.clouder.clouderapi.pojo.Cloud;

@FunctionalInterface
public interface AutheticateUserCloudService {

    Cloud addCloud(String username, String... codes);

}
