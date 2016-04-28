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
import com.clouder.clouderapi.dto.UsernamePasswordDTO;
import com.clouder.clouderapi.pojo.Constants;
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
        UsernamePasswordDTO usernamePasswordDTO = jsonUtility.toObject(json, UsernamePasswordDTO.class);
        User dbUser = userRepository.findByUsername(usernamePasswordDTO.getUsername());
        String privateKeyBase64 = dbUser.getPrivateKey();
        PrivateKey privatekey = keyGenerationService.getPrivateKeyFromBase64(privateKeyBase64);
        String password = keyGenerationService.decrypt(usernamePasswordDTO.getPassword(), privatekey);
        String passwordHash = keyGenerationService.encodeString(password);
        dbUser.setPassword(passwordHash);
        User user = userRepository.save(dbUser);
        String emailLink = getEmailLink(dbUser.getUsername());
        emailService.sendEmail(dbUser.getEmailId(), null, "Email Verification",
                "Click on the following link to verify your email account<br>" + emailLink);
        return user;
    }

    private String getEmailLink(String username) {
        String currentTimeEncoded = new String(
                Base64.encodeBase64(String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8)));
        return baseUrl + "user/verifyemail?username=" + username + "&key=" + currentTimeEncoded;
    }

    @Override
    public boolean verifyUser(String username, String key) {
        try {
            long timestamp = Long.parseLong(new String(Base64.decodeBase64(key)));
            if (System.currentTimeMillis() - timestamp > Constants.TIMEOUT_MINUTES * 60 * 1000) {
                return false;
            }
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return false;
            }
            user.setVerified(true);
            userRepository.save(user);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
