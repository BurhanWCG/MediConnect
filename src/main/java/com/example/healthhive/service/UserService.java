package com.example.healthhive.service;

import com.example.healthhive.DTO.UserDTO;
import com.example.healthhive.model.User;
import com.example.healthhive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to register a new user
    public User registerUser(User user) {
        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Set the encrypted password

        // Save the user to the database
        return userRepository.save(user);
    }

    // Method to convert User to UserDTO
    public UserDTO getUserDTO(User user) {
        // Convert the User to UserDTO
        return new UserDTO(user.getName(), user.getEmail(), user.getRole().name());
    }
}
