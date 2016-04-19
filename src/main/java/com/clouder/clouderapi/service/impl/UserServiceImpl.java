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
	public boolean saveUser(String json) {
		boolean inserted = false;
		User user = jsonUtility.toObject(json, User.class);
		User insertedUser = userRepository.save(user);
		if (insertedUser != null)
			inserted = true;
		return inserted;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public String getPublicKey(User user) {
		// TODO Auto-generated method stub
		return userRepository.findPublicKey(user.getUsername());
	}

	@Override
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
		boolean inserted = false;
		User insertedUser = userRepository.save(user);
		if(insertedUser != null)
			inserted = true;
		return inserted;
	}
	
	

}
