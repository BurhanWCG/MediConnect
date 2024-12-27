package com.example.healthhive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // This makes it a Spring configuration class
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow cross-origin requests from the frontend or Android emulator
        registry.addMapping("/**") // Apply this to all endpoints
                .allowedOrigins("http://localhost:3000", "http://10.0.2.2") // Frontend URLs or emulator
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
                .allowCredentials(true); // Allow credentials (e.g., cookies or headers)
    }
}
