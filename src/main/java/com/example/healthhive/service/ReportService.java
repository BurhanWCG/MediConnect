package com.example.healthhive.service;

import com.example.healthhive.DTO.ReportDTO;
import com.example.healthhive.model.Report;
import com.example.healthhive.model.User;
import com.example.healthhive.repository.ReportRepository;
import com.example.healthhive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    public void submitReport(ReportDTO reportDTO) {
        // Validate the doctor
        Optional<User> doctorOpt = userRepository.findByName(reportDTO.getDoctorName());
        if (doctorOpt.isEmpty()) {
            throw new IllegalArgumentException("Doctor not found");
        }

        // Validate the patient
        Optional<User> patientOpt = userRepository.findByName(reportDTO.getPatientName());
        if (patientOpt.isEmpty()) {
            throw new IllegalArgumentException("Patient not found");
        }

        // Create and save the report
        Report report = new Report();
        report.setDetails(reportDTO.getDetails());
        report.setReportDate(new Date());
        report.setDoctor(doctorOpt.get());
        report.setPatient(patientOpt.get());  // Set the patient

        reportRepository.save(report);
    }
    public List<ReportDTO> getReports() {
        // Get the logged-in user's email from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println(username);

        // Fetch the user from the repository based on the email
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found for email: " + username);
        }

        User user = userOpt.get();

        // Fetch the reports for the logged-in user (if they are a patient, we fetch their reports)
        List<Report> reports = user.getReports();

        // Map the reports to ReportDTO with only doctorName, reportDate, and details
        return reports.stream().map(report -> new ReportDTO(
                report.getDoctor().getName(),
                report.getPatient().getName(),
                report.getDetails()
        )).collect(Collectors.toList());
    }
}
