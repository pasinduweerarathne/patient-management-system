package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    boolean existsByEmail(String email);

    // It checks if **another patient exists with the given email but a different ID.
    boolean existsByEmailAndIdNot(String email, Integer id);
}
