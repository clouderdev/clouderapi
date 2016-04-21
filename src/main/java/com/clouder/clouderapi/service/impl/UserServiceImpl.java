package com.clouder.clouderapi.service.impl;

import java.security.PrivateKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.repository.UserRepository;
import com.clouder.clouderapi.service.KeyGenerationService;
import com.clouder.clouderapi.service.UserService;
import com.clouder.clouderapi.util.JsonUtility;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JsonUtility jsonUtility;

    @Autowired
    KeyGenerationService keyGenerationService;

    @Override
    public User saveUser(String json) {
        User user = jsonUtility.toObject(json, User.class);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User savePassword(String json) {
        User user = jsonUtility.toObject(json, User.class);
        User dbUser = userRepository.findByUsername(user.getUsername());
        String privateKeyBase64 = dbUser.getPrivateKey();
        PrivateKey privatekey = keyGenerationService.getPrivateKeyFromBase64(privateKeyBase64);
        String password = keyGenerationService.decrypt(user.getPassword(), privatekey);
        String passwordHash = keyGenerationService.hash(password);
        dbUser.setPassword(passwordHash);
        return userRepository.save(dbUser);
    }

}
