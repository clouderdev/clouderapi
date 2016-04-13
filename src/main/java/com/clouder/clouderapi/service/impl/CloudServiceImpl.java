package com.clouder.clouderapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.Cloud;
import com.clouder.clouderapi.pojo.GoogleDrive;
import com.clouder.clouderapi.repository.CloudRepository;
import com.clouder.clouderapi.service.CloudService;

@Service
public class CloudServiceImpl implements CloudService {

	@Autowired
	CloudRepository cloudRepository;

	@Override
	public List<Cloud> getClouds() {
		Cloud cloud = new GoogleDrive("accessToken", "refreshToken");

		System.out.println(cloud.getCloudTypeText());

		cloudRepository.save(cloud);

		List<Cloud> clouds = cloudRepository.findAll();
		System.out.println(clouds.get(0).getCloudType());
		return clouds;
	}

}
