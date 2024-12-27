package com.example.healthhive.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true)  // Ensure name is unique
    private String name;

    @Column(name = "email", unique = true)  // Ensure email is unique
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Unidirectional: User has appointments, but Appointment doesn't need to know about User
    @OneToMany(mappedBy = "patient")  // No need to use @JsonIgnore because there is no circular dependency
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")  // Unidirectional relationship to Doctor's appointments
    private List<Appointment> doctorAppointments;


    @OneToMany(mappedBy = "patient")  // Unidirectional relation for reports
    private List<Report> reports;

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Appointment> getDoctorAppointments() {
        return doctorAppointments;
    }

    public void setDoctorAppointments(List<Appointment> doctorAppointments) {
        this.doctorAppointments = doctorAppointments;
    }


    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    // Role Enum with JSON Creator
    public enum Role {
        DOCTOR, PATIENT, ADMIN;

        @JsonCreator
        public static Role fromString(String value) {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException("Role cannot be null or empty");
            }
            try {
                return Role.valueOf(value.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role: " + value + ". Allowed roles: DOCTOR, PATIENT, ADMIN");
            }
        }
    }
}
