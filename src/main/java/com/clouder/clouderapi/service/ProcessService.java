package com.clouder.clouderapi.service;

import java.util.List;

import com.clouder.clouderapi.document.User;

public interface ProcessService {
	void saveUser();

	List<User> getUsers();
}
