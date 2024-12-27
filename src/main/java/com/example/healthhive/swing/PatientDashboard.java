package com.example.healthhive.swing;

import javax.swing.*;
import java.awt.*;

public class PatientDashboard extends JPanel {

    private String jwtToken;

    public PatientDashboard(String jwtToken) {
        this.jwtToken = jwtToken;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Sidebar Navigation
        JPanel sidebar = new JPanel();
//        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
//        JButton btnTakeAppointment = new JButton("Take Appointment");
//        JButton btnViewRecords = new JButton("View Health Records");
//        JButton btnViewReports = new JButton("View Reports");
//
//        sidebar.add(btnTakeAppointment);
//        sidebar.add(btnViewRecords);
//        sidebar.add(btnViewReports);

        // Main Content
        JLabel welcomeLabel = new JLabel("Welcome to Patient Dashboard", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        add(sidebar, BorderLayout.WEST);
        add(welcomeLabel, BorderLayout.CENTER);
    }
}
