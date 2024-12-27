package com.example.healthhive.service;

import com.example.healthhive.DTO.AppointmentDTO;
import com.example.healthhive.model.Appointment;
import com.example.healthhive.model.User;
import com.example.healthhive.repository.AppointmentRepository;
import com.example.healthhive.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public String createAppointment(AppointmentDTO appointmentDTO) {
        // Find patient and doctor by name (which is unique)
        Optional<User> patientOpt = userRepository.findByName(appointmentDTO.getPatientName());
        Optional<User> doctorOpt = userRepository.findByName(appointmentDTO.getDoctorName());

        if (patientOpt.isEmpty()) {
            return "Patient not found.";
        }

        if (doctorOpt.isEmpty()) {
            return "Doctor not found.";
        }

        // Create appointment entity
        User patient = patientOpt.get();
        User doctor = doctorOpt.get();

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());

        // Save the appointment
        appointmentRepository.save(appointment);
        return "Appointment created successfully.";
    }
    @Transactional
    public List<AppointmentDTO> getAllAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);

        Optional<User> doctorOpt = userRepository.findByEmail(username);
        if (doctorOpt.isEmpty()) {
            throw new RuntimeException("Doctor not found for username: " + username);
        }
        User doctor = doctorOpt.get();

        // Check the doctor's appointments
        List<Appointment> appointments = doctor.getDoctorAppointments();
        System.out.println("Appointments for doctor: " + appointments.size());

        return appointments.stream().map(appointment -> {
            AppointmentDTO dto = new AppointmentDTO();
            dto.setPatientName(appointment.getPatient().getName());
            dto.setDoctorName(appointment.getDoctor().getName());
            dto.setAppointmentDate(appointment.getAppointmentDate());
            return dto;
        }).collect(Collectors.toList());
    }


}
