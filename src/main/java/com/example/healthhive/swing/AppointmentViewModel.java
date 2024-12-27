package com.example.healthhive.swing;

import java.time.LocalDateTime;

public class AppointmentViewModel {

    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentDate;

    // Constructor
    public AppointmentViewModel(String patientName, String doctorName, LocalDateTime appointmentDate) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
    }

    // Getters and Setters
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
