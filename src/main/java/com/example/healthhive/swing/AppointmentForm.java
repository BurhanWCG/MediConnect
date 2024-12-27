package com.example.healthhive.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AppointmentForm extends JPanel {
    private String jwtToken;

    public AppointmentForm(String jwtToken) {
        this.jwtToken = jwtToken;
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Appointment Form");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        JLabel patientNameLabel = new JLabel("Patient Name:");
        JTextField patientNameField = new JTextField(20);
        JLabel doctorNameLabel = new JLabel("Doctor Name:");
        JTextField doctorNameField = new JTextField(20);
        JLabel dateLabel = new JLabel("Date (yyyy-MM-dd):");
        JTextField dateField = new JTextField(20);
        JLabel timeLabel = new JLabel("Time (HH:mm):");
        JTextField timeField = new JTextField(20);

        // Add components to form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(patientNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(patientNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(doctorNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(doctorNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(timeField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Submit Button
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Schedule Appointment");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from the form fields
                String patientName = patientNameField.getText();
                String doctorName = doctorNameField.getText();
                String date = dateField.getText();
                String time = timeField.getText();

                // Validate and format appointmentDate
                try {
                    String appointmentDate = validateAndFormatDateTime(date, time);

                    // Call the method to send the HTTP request with JWT
                    sendAppointmentRequest(patientName, doctorName, appointmentDate, jwtToken);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(AppointmentForm.this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AppointmentForm.this, "Error scheduling appointment.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(submitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to validate and format date and time
    private String validateAndFormatDateTime(String date, String time) {
        try {
            // Combine date and time into LocalDateTime
            LocalDateTime appointmentDateTime = LocalDateTime.parse(date + "T" + time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return appointmentDateTime.toString(); // Return ISO 8601 format
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date or time format. Please use 'yyyy-MM-dd' for date and 'HH:mm' for time.");
        }
    }

    // Method to send the HTTP request with the form data and JWT token
    private void sendAppointmentRequest(String patientName, String doctorName, String appointmentDate, String jwtToken) throws Exception {
        // The backend endpoint URL
        String endpoint = "http://localhost:8082/api/appointments/create"; // Change URL as needed

        // Prepare JSON data
        String jsonInputString = String.format("{\"patientName\": \"%s\", \"doctorName\": \"%s\", \"appointmentDate\": \"%s\"}",
                patientName, doctorName, appointmentDate);

        // Create URL and open connection
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method to POST
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + jwtToken);
        connection.setDoOutput(true);

        // Send JSON data in the request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Get the response code to confirm the request was successful
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JOptionPane.showMessageDialog(this, "Appointment scheduled successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to schedule appointment. HTTP Code: " + responseCode, "Error", JOptionPane.ERROR_MESSAGE);
        }

        connection.disconnect();
    }
}
