package com.example.healthhive.model;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String details;

    @Temporal(TemporalType.DATE)
    private Date reportDate;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient; // Reference to the patient (User entity)

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;  // Reference to the doctor (User entity)

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Date getDate(){
        return reportDate;
    }

    public void setDate (Date reportDate){
        this.reportDate = reportDate;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }
}
