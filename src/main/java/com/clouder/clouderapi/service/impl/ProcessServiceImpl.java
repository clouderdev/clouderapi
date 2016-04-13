package com.clouder.clouderapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.Cloud;
import com.clouder.clouderapi.document.DropBox;
import com.clouder.clouderapi.document.GoogleDrive;
import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.repository.UserRepository;
import com.clouder.clouderapi.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService {

	@Autowired
	UserRepository userRepository;

	@Override
	public void saveUser() {
		Cloud googleDrive = new GoogleDrive("GD", "accessToken", "refreshToken");
		Cloud dropBox = new DropBox("DB", "dropBoxAccessToken",
				"dropBoxRefreshToken");
		List<Cloud> clouds = new ArrayList<>();
		clouds.add(googleDrive);
		clouds.add(dropBox);

		User user = new User("ssshukla1993@gmail.com", "Shrinivas", "Shukla",
				"shrinivas93", "P@ssw0rd", clouds);
		userRepository.save(user);
	}

}
