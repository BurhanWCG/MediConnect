package com.example.healthhive.controller;

import com.example.healthhive.DTO.UserDTO;
import com.example.healthhive.model.User;
import com.example.healthhive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        // Let the UserService handle password encryption and saving
        User savedUser = userService.registerUser(user);
        UserDTO userDTO = userService.getUserDTO(savedUser);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
}
