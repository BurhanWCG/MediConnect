package com.example.healthhive.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReportsScreen extends JPanel {
    private JTable reportsTable;
    private DefaultTableModel tableModel;
    private String jwtToken; // Authentication token

    public ReportsScreen(String jwtToken) {
        this.jwtToken = jwtToken;
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Reports");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columnNames = {"Doctor Name", "Patient Name", "Report Details"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(reportsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load reports from the backend
        loadReports();
    }

    private void loadReports() {
        try {
            // Send HTTP GET request to fetch reports
            URL url = new URL("http://localhost:8082/api/reports/userReports");  // Replace with your backend URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + jwtToken);
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response and populate the table
                List<ReportViewModel> reports = parseReports(response.toString());
                populateTable(reports);

            } else {
                JOptionPane.showMessageDialog(this, "Failed to load reports. HTTP Code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading reports: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<ReportViewModel> parseReports(String json) {
        List<ReportViewModel> reports = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String doctorName = obj.getString("doctorName");
                String patientName = obj.getString("patientName");
                String reportDetails = obj.getString("details");

                reports.add(new ReportViewModel(doctorName, patientName, reportDetails));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error parsing reports: " + e.getMessage());
            e.printStackTrace();
        }
        return reports;
    }

    private void populateTable(List<ReportViewModel> reports) {
        tableModel.setRowCount(0); // Clear existing data in table

        for (ReportViewModel report : reports) {
            tableModel.addRow(new Object[]{
                    report.getDoctorName(),
                    report.getPatientName(),
                    report.getDetails()
            });
        }
    }
}
