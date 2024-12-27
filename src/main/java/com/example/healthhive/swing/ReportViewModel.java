package com.example.healthhive.swing;

public class ReportViewModel {
    private String doctorName;
    private String patientName;
    private String details;

    // Constructor
    public ReportViewModel(String doctorName, String patientName, String details) {
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.details = details;
    }

    // Getters and Setters
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
