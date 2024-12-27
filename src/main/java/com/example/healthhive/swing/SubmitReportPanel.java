package com.example.healthhive.swing;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubmitReportPanel extends JPanel {

    private String token;
    private String patientName;
    private String doctorName;
    private JTextArea reportDetailsField;

    // Constructor to initialize the panel with patient and doctor details
    public SubmitReportPanel(String token, String patientName, String doctorName, Object appointmentDate) {
        this.token = token;
        this.patientName = patientName;
        this.doctorName = doctorName;

        // Set layout manager
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Centering the whole content in the panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title Label
        JLabel titleLabel = new JLabel("Submit Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20)); // Add space between title and form

        // Patient and Doctor Information
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Padding around form
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(new JLabel("Patient:"));
        formPanel.add(new JLabel(patientName));
        formPanel.add(new JLabel("Doctor:"));
        formPanel.add(new JLabel(doctorName));
        formPanel.add(new JLabel("Appointment Date:"));
        formPanel.add(new JLabel(appointmentDate.toString()));

        contentPanel.add(formPanel);
        contentPanel.add(Box.createVerticalStrut(20)); // Add space between form and text area

        // Add text area for report details
        reportDetailsField = new JTextArea(5, 30);
        reportDetailsField.setLineWrap(true);
        reportDetailsField.setWrapStyleWord(true);
        reportDetailsField.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(reportDetailsField);
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createVerticalStrut(20)); // Add space between text area and button

        // Submit Button
        JButton submitButton = new JButton("Submit Report");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> submitReport());
        contentPanel.add(submitButton);

        // Add content panel to the main panel
        add(contentPanel, BorderLayout.CENTER);
    }

    private void submitReport() {
        try {
            // Get the report details and escape newlines
            String reportDetails = reportDetailsField.getText().replace("\n", "\\n");

            // Send HTTP POST request to submit the report
            URL url = new URL("http://localhost:8082/api/reports/submit");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            // Construct the JSON request payload with escaped newlines
            String requestBody = String.format(
                    "{\"patientName\": \"%s\", \"doctorName\": \"%s\", \"details\": \"%s\"}",
                    patientName, doctorName, reportDetails
            );

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes());
                os.flush();
            }

            if (connection.getResponseCode() == 200) {
                JOptionPane.showMessageDialog(this, "Report submitted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit report. HTTP Error Code: " + connection.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting report: " + e.getMessage());
        }
    }
}
