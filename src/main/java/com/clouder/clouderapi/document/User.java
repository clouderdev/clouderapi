package com.clouder.clouderapi.document;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.clouder.clouderapi.pojo.Cloud;

@Document(collection = "user")
public class User {

    @Id
    @JsonIgnore
    private String id;

    private String emailId;

    private String firstName;

    private String lastName;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String publicKey;

    @JsonIgnore
    private String privateKey;

    private List<Cloud> clouds;

    private boolean isVerified;

    public User() {
        // Default constructor for Jackson
    }

    public User(String emailId, String firstName, String lastName, String username, String password,
            List<Cloud> clouds) {
        super();
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.clouds = clouds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public void setClouds(List<Cloud> clouds) {
        this.clouds = clouds;
    }

    public boolean addCloud(Cloud cloud) {
        if (clouds == null) {
            clouds = new ArrayList<>();
        }
        return clouds.add(cloud);
    }

    public Cloud getCloud(String email) {
        if (clouds == null) {
            return null;
        }
        for (Cloud cloud : clouds) {
            if (cloud.getEmail().equals(email)) {
                return cloud;
            }
        }
        return null;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", emailId=" + emailId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", username=" + username + ", password=" + password + ", publicKey=" + publicKey + ", privateKey="
                + privateKey + ", clouds=" + clouds + ", isVerified=" + isVerified + "]";
    }

}
