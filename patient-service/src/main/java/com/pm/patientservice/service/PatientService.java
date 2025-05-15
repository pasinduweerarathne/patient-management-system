package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.email())) {
            throw new EmailAlreadyExistsException("A patient with this email " + patientRequestDTO.email() + " is already exists.");
        }

        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(Integer id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.email(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email " + patientRequestDTO.email() + " is already exists.");
        }

        // we can do this but we have to provide all the values. but we don't have to provide id and registeredDate values to update
        /*Patient updatedPatient = Patient.builder()
                .id(patient.getId())
                .name(patientRequestDTO.name())
                .address(patientRequestDTO.address())
                .email(patientRequestDTO.email())
                .dateOfBirth(LocalDate.parse(patientRequestDTO.dateOfBirth()))
                .registeredDate(patient.getRegisteredDate())
                .build();*/

        // proper way
        patient.setName(patientRequestDTO.name());
        patient.setAddress(patientRequestDTO.address());
        patient.setEmail(patientRequestDTO.email());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.dateOfBirth()));

        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    public void deletePatient(Integer id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with ID: " + id);
        }

        patientRepository.deleteById(id);
    }
}
