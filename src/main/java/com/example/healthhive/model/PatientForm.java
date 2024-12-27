package com.example.healthhive.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient_forms")
public class PatientForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicalHistory;
    private String currentSymptoms;
    private Date formDate;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private User patient;  // References User who is the patient

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getCurrentSymptoms() {
        return currentSymptoms;
    }

    public void setCurrentSymptoms(String currentSymptoms) {
        this.currentSymptoms = currentSymptoms;
    }

    public Date getFormDate() {
        return formDate;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }
}
