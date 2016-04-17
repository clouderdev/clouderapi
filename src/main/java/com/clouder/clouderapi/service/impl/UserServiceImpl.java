package com.clouder.clouderapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.repository.UserRepository;
import com.clouder.clouderapi.service.UserService;
import com.clouder.clouderapi.util.JsonUtility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JsonUtility jsonUtility;

	@Override
	public void saveUser(String json) {
		User user = jsonUtility.toObject(json, User.class);
		userRepository.save(user);
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

}
