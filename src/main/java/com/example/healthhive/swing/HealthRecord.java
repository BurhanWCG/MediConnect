package com.example.healthhive.swing;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HealthRecord extends JPanel {
    private String jwtToken;

    // Constructor to accept JWT token and the list of ReportViewModel
    public HealthRecord(String jwtToken, List<ReportViewModel> reportsList) {
        this.jwtToken = jwtToken;  // Save the token
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Health Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea healthRecordArea = new JTextArea(10, 30);
        healthRecordArea.setEditable(false);

        // Build the string dynamically by iterating over the reportsList and appending only the details
        StringBuilder records = new StringBuilder();
        for (ReportViewModel report : reportsList) {
            records.append("- ").append(report.getDetails()).append("\n");  // Display only the details
        }

        // Set the text area to the dynamically built string
        healthRecordArea.setText(records.toString());

        JScrollPane scrollPane = new JScrollPane(healthRecordArea);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Method to get the JWT token (if needed elsewhere in the class)
    public String getJwtToken() {
        return jwtToken;
    }
}
