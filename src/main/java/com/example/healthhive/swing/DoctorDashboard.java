package com.example.healthhive.swing;

import javax.swing.*;
import java.awt.*;

public class DoctorDashboard extends JPanel {
    String jwtToken;

    public DoctorDashboard(String jwtToken) {
        this.jwtToken= jwtToken;
        initializeUI();
    }

    private void initializeUI() {
        // Set layout for the dashboard
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY); // Neutral background color

        // Create the header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLUE); // Blue header background
        headerPanel.setPreferredSize(new Dimension(0, 60)); // Fixed height

        JLabel welcomeLabel = new JLabel("Doctor Dashboard", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel);

        // Add panels to the dashboard
        add(headerPanel, BorderLayout.NORTH);

        // Placeholder for main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.GRAY); // Gray footer background
        footerPanel.setPreferredSize(new Dimension(0, 30)); // Fixed height

        JLabel footerLabel = new JLabel("MediConnect © 2024", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);

        add(footerPanel, BorderLayout.SOUTH);
    }
}
