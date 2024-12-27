package com.example.healthhive.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ViewAppointmentsPanel extends JPanel {

    private JTable appointmentsTable;
    private DefaultTableModel tableModel;
    private String token; // Auth token
    private JFrame parentFrame; // For navigation

    // Constructor to initialize the panel and pass the parentFrame
    public ViewAppointmentsPanel(JFrame parentFrame, String token) {
        this.parentFrame = parentFrame; // Initialize parentFrame
        this.token = token;  // Initialize the token

        setLayout(new BorderLayout());

        // Table columns
        String[] columnNames = {"Patient Name", "Doctor Name", "Appointment Date", "Submit Report"};
        tableModel = new DefaultTableModel(columnNames, 0);
        appointmentsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Add click listener for navigation
        appointmentsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = appointmentsTable.columnAtPoint(evt.getPoint());
                int row = appointmentsTable.rowAtPoint(evt.getPoint());
                if (column == 3) { // Submit Report column clicked
                    navigateToSubmitReport(row);
                }
            }
        });

        // Load appointments from the backend
        loadAppointments();
    }

    private void loadAppointments() {
        try {
            // Send HTTP GET request to fetch appointments
            URL url = new URL("http://localhost:8082/api/appointments/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);
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
                List<AppointmentViewModel> appointments = parseAppointments(response.toString());
                populateTable(appointments);

            } else {
                JOptionPane.showMessageDialog(this, "Failed to load appointments. HTTP Code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<AppointmentViewModel> parseAppointments(String json) {
        List<AppointmentViewModel> appointments = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String patientName = obj.getString("patientName");
                String doctorName = obj.getString("doctorName");
                LocalDateTime appointmentDate = LocalDateTime.parse(obj.getString("appointmentDate"));

                appointments.add(new AppointmentViewModel(patientName, doctorName, appointmentDate));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error parsing appointments: " + e.getMessage());
            e.printStackTrace();
        }
        return appointments;
    }

    private void populateTable(List<AppointmentViewModel> appointments) {
        tableModel.setRowCount(0);

        for (AppointmentViewModel appointment : appointments) {
            tableModel.addRow(new Object[] {
                    appointment.getPatientName(),
                    appointment.getDoctorName(),
                    appointment.getAppointmentDate(),
                    "Submit Report"
            });
        }
    }

    private void navigateToSubmitReport(int row) {
        String patientName = tableModel.getValueAt(row, 0).toString();
        String doctorName = tableModel.getValueAt(row, 1).toString();
        Object appointmentDate = tableModel.getValueAt(row, 2);  // Get appointment date

        // Navigate to SubmitReportPanel
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new SubmitReportPanel(token, patientName, doctorName, appointmentDate));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
