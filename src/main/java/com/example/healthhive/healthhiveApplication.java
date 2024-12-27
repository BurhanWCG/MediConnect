package com.example.healthhive;

import com.example.healthhive.swing.MainFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication(scanBasePackages = {"com.example.healthhive", "com.example.healthhive.swing"})

public class healthhiveApplication {

	public static void main(String[] args) {
		// Launch Spring Boot application
		SpringApplication.run(healthhiveApplication.class, args);
	}

	@Bean
	public MainFrame mainFrame() {
		// Check if the environment is headless
		if (!GraphicsEnvironment.isHeadless()) {
			MainFrame mainFrame = new MainFrame();
			SwingUtilities.invokeLater(() -> mainFrame.setVisible(true));
			return mainFrame;
		} else {
			// Handle the case when running in a headless environment
			System.out.println("Headless environment detected, skipping Swing GUI initialization.");
			return null;  // Or return a fallback if necessary
		}
	}
}
