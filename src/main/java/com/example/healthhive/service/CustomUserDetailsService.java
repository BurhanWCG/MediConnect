package com.example.healthhive.service;

import com.example.healthhive.DTO.LoginRequest;
import com.example.healthhive.model.User;
import com.example.healthhive.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user by username from the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));



        // Convert your User entity to Spring Security's UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()) // Set the email as the username
                .password(user.getPassword()) // Set the encrypted password
                .authorities(mapRolesToAuthorities(user.getRole()))// No roles for now (can add roles if needed)
                .build();
    }
    private List<SimpleGrantedAuthority> mapRolesToAuthorities(User.Role role) {
        // We add "ROLE_" prefix to role as per Spring Security convention
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    }


