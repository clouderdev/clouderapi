package com.clouder.clouderapi.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.repository.UserRepository;
import com.clouder.clouderapi.service.EmailService;
import com.clouder.clouderapi.service.KeyGenerationService;
import com.clouder.clouderapi.service.UserService;
import com.clouder.clouderapi.util.JsonUtility;

@Service
public class UserServiceImpl implements UserService {

    @Value("${base.url}")
    String baseUrl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JsonUtility jsonUtility;

    @Autowired
    KeyGenerationService keyGenerationService;

    @Autowired
    EmailService emailService;

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
    public User savePassword(String json) throws MessagingException {
        User user = jsonUtility.toObject(json, User.class);
        User dbUser = userRepository.findByUsername(user.getUsername());
        String privateKeyBase64 = dbUser.getPrivateKey();
        PrivateKey privatekey = keyGenerationService.getPrivateKeyFromBase64(privateKeyBase64);
        String password = keyGenerationService.decrypt(user.getPassword(), privatekey);
        String passwordHash = keyGenerationService.encodeString(password);
        dbUser.setPassword(passwordHash);
        String emailLink = getEmailLink(user.getUsername());
        emailService.sendEmail(user.getEmailId(), null, "Email Verification",
                "Click on the following link to verify your email account<br>" + emailLink);
        return userRepository.save(dbUser);
    }

    private String getEmailLink(String username) {
        String currentTimeEncoded = new String(
                Base64.encodeBase64(String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8)));
        return baseUrl + "verifyemail?username=" + username + "&key=" + currentTimeEncoded;
    }

}
