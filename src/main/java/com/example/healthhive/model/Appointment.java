package com.example.healthhive.model;

import jakarta.persistence.*;
import java.util.UUID;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)  // Only one side knows about the other
    @JoinColumn(name = "patient_id")  // This is the foreign key
    private User patient;  // The patient for this appointment

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")  // This is the foreign key
    private User doctor;  // The doctor for this appointment

    private LocalDateTime appointmentDate;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
