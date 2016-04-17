package com.clouder.clouderapi.service;

import java.util.List;

import com.clouder.clouderapi.document.User;

public interface UserService {
	void saveUser(String json);

	List<User> getUsers();
}
