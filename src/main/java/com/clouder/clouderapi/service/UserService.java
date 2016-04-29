package com.clouder.clouderapi.service;

import java.util.List;

import javax.mail.MessagingException;

import com.clouder.clouderapi.document.User;

public interface UserService {
    User saveUser(String json);

    List<User> getUsers();

    User findByUsername(String username);

    User saveUser(User user);

    User savePassword(String json) throws MessagingException;

    boolean verifyUser(String username, String key);

    String login(String json);

    User getUserFromToken(String token);
}
