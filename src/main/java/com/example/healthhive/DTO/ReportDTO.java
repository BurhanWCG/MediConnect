package com.example.healthhive.DTO;

public class ReportDTO {
    private String details;
    private String doctorName;
    private String patientName;


    public ReportDTO(String doctorName, String patientName, String details) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.details = details;

    }

    // Getters and Setters
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

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
}
