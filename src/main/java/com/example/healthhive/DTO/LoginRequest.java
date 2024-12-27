package com.example.healthhive.DTO;

public class LoginRequest {

    private String email;
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;  // Changed from getUsername to getEmail
    }

    public void setEmail(String email) {
        this.email = email;  // Changed from setUsername to setEmail
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
