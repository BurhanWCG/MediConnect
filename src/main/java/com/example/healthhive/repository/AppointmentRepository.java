package com.example.healthhive.repository;

import com.example.healthhive.model.Appointment;
import com.example.healthhive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // Optional: Find Appointment by patient and doctor names
    Optional<Appointment> findByPatientAndDoctor(User patient, User doctor);
}
