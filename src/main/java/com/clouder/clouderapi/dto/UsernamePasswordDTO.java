package com.clouder.clouderapi.dto;

public class UsernamePasswordDTO {
    private String username;
    private String password;

    public UsernamePasswordDTO() {
        // Default constructor
    }

    public UsernamePasswordDTO(String username, String password) {
        super();
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "UsernamePasswordDTO [username=" + username + ", password=" + password + "]";
    }

}
