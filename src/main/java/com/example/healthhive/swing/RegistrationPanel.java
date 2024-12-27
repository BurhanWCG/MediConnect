package com.example.healthhive.swing;

import com.example.healthhive.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegistrationPanel extends JPanel {

    public RegistrationPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        JLabel roleLabel = new JLabel("Role:");
        JComboBox<User.Role> roleComboBox = new JComboBox<>(User.Role.values());

        JButton registerButton = new JButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(roleLabel, gbc);
        gbc.gridx = 1;
        add(roleComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(registerButton, gbc);

        // Action when register button is clicked
        registerButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            User.Role selectedRole = (User.Role) roleComboBox.getSelectedItem(); // Get selected role

            // Validate input fields
            if (email.isEmpty() || password.isEmpty() || selectedRole == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            // Send the user data to the server
            try {
                URL url = new URL("http://localhost:8082/api/users/register"); // Replace with your server URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Construct JSON body
                String jsonInputString = "{"
                        + "\"name\": \"" + username + "\","
                        + "\"email\": \"" + email + "\","
                        + "\"password\": \"" + password + "\","
                        + "\"role\": \"" + selectedRole + "\""
                        + "}";

                // Send JSON data
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    JOptionPane.showMessageDialog(this, "Registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed. Response code: " + responseCode);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred. Please try again. Error: " + ex.getMessage());
            }
        });
    }
}
