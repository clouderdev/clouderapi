package com.clouder.clouderapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.Cloud;
import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.pojo.DropBox;
import com.clouder.clouderapi.pojo.GoogleDrive;
import com.clouder.clouderapi.repository.CloudRepository;
import com.clouder.clouderapi.repository.UserRepository;
import com.clouder.clouderapi.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CloudRepository cloudRepository;

	@Override
	public void saveUser() {
		Cloud googleDrive = new GoogleDrive("accessToken", "refreshToken");
		Cloud dropBox = new DropBox("dropBoxAccessToken", "dropBoxRefreshToken");
		List<Cloud> clouds = new ArrayList<>();
		clouds.add(googleDrive);
		clouds.add(dropBox);

		cloudRepository.save(clouds);

		User user = new User("ssshukla1993@gmail.com", "Shrinivas", "Shukla", "shrinivas93", "P@ssw0rd", clouds);
		userRepository.save(user);
	}

	@Override
	public List<User> getUsers() {
		List<User> users;
		users = userRepository.findAll();
		User user = users.get(0);
		List<Cloud> clouds = user.getClouds();
		for (Cloud cloud : clouds) {
			System.out.println(cloud);
		}
		return users;
	}

}
