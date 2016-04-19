package com.clouder.clouderapi.service;

import java.util.List;

import com.clouder.clouderapi.document.User;

public interface UserService {
    boolean saveUser(String json);

    List<User> getUsers();

    User findByUsername(String username);

    boolean saveUser(User user);
}
