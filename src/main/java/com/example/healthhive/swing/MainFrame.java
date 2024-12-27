package com.example.healthhive.swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardContainer;
    private JPanel navigationPanel;

    public MainFrame() {
        setTitle("MediConnect");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CardLayout container for switching panels
        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);

        // Panels
        LoginPanel loginPanel = new LoginPanel(this, cardLayout, cardContainer);
        RegistrationPanel registrationPanel = new RegistrationPanel();

        // Add initial panels to cardContainer
        cardContainer.add(loginPanel, "Login");
        cardContainer.add(registrationPanel, "Register");

        // Initial navigation panel with login and registration buttons
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton loginButton = new JButton("Go to Login");
        JButton registerButton = new JButton("Go to Register");

        // Action listeners for buttons
        loginButton.addActionListener(e -> {
            cardLayout.show(cardContainer, "Login");
        });

        registerButton.addActionListener(e -> {
            cardLayout.show(cardContainer, "Register");
        });

        // Add buttons to navigation panel
        navigationPanel.add(loginButton);
        navigationPanel.add(registerButton);

        // Add the cardContainer and navigationPanel to the frame
        setLayout(new BorderLayout());
        add(navigationPanel, BorderLayout.SOUTH);  // Navigation buttons at the bottom
        add(cardContainer, BorderLayout.CENTER);   // Card container in the center

        // Show Login panel initially
        cardLayout.show(cardContainer, "Login");
    }

    /**
     * Navigate to the appropriate dashboard based on the user's role.
     *
     * @param role     The role of the user (DOCTOR or PATIENT)
     * @param jwtToken The JWT token received after login
     */
    public void navigateToDashboard(String role, String jwtToken) {
        // Remove the navigation panel buttons after login/registration
        navigationPanel.removeAll();
        // Add role-specific navigation buttons
        addRoleSpecificNavigationButtons(role, jwtToken);

        if ("ROLE_DOCTOR".equalsIgnoreCase(role)) {
            DoctorDashboard doctorDashboard = new DoctorDashboard(jwtToken);
            cardContainer.add(doctorDashboard, "DoctorDashboard");
            cardLayout.show(cardContainer, "DoctorDashboard");
        } else if ("ROLE_PATIENT".equalsIgnoreCase(role)) {
            PatientDashboard patientDashboard = new PatientDashboard(jwtToken);
            cardContainer.add(patientDashboard, "PatientDashboard");
            cardLayout.show(cardContainer, "PatientDashboard");
        } else {
            JOptionPane.showMessageDialog(this, "Unknown role: " + role, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRoleSpecificNavigationButtons(String role, String jwtToken) {

        // Role-specific buttons
        if ("ROLE_PATIENT".equalsIgnoreCase(role)) {
            // Patient-specific buttons
            JButton appointmentButton = new JButton("Take Appointment");
//            JButton healthRecordButton = new JButton("View Health Records");
            JButton reportsButton = new JButton("View Reports");

            // Add patient screens to cardContainer
            AppointmentForm appointmentForm = new AppointmentForm(jwtToken);
//            HealthRecord healthRecord = new HealthRecord();
            ReportsScreen reportScreen = new ReportsScreen(jwtToken);

            cardContainer.add(appointmentForm, "AppointmentForm");
//            cardContainer.add(healthRecord, "HealthRecord");
            cardContainer.add(reportScreen, "ReportScreen");

            // Action listeners for patient buttons
            appointmentButton.addActionListener(e -> {
                cardLayout.show(cardContainer, "AppointmentForm");
            });

//            healthRecordButton.addActionListener(e -> {
//                cardLayout.show(cardContainer, "HealthRecord");
//            });

            reportsButton.addActionListener(e -> {
                cardLayout.show(cardContainer, "ReportScreen");
            });

            // Add buttons to navigation panel
            navigationPanel.add(appointmentButton);
//            navigationPanel.add(healthRecordButton);
            navigationPanel.add(reportsButton);

        } else if ("ROLE_DOCTOR".equalsIgnoreCase(role)) {
            // Doctor-specific buttons
            JButton viewAppointmentsButton = new JButton("View Appointments");
            JButton submitReportButton = new JButton("Submit Report");


            ViewAppointmentsPanel viewAppointmentsPanel = new ViewAppointmentsPanel(this,jwtToken); // Pass token to ViewAppointmentsPanel
            // Create a screen for adding reports

            cardContainer.add(viewAppointmentsPanel, "ViewAppointmentsPanel");
//            cardContainer.add(addReportForm, "AddReportForm");

            // Action listeners for doctor buttons
            viewAppointmentsButton.addActionListener(e -> {
                cardLayout.show(cardContainer, "ViewAppointmentsPanel");
            });

            submitReportButton.addActionListener(e -> {
                cardLayout.show(cardContainer, "SubmitReport");
            });

            // Add buttons to navigation panel
            navigationPanel.add(viewAppointmentsButton);
            navigationPanel.add(submitReportButton);
        }

        // Refresh navigation panel and container
        navigationPanel.revalidate();
        navigationPanel.repaint();
        cardContainer.revalidate();
        cardContainer.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
