package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
public record PatientRequestDTO(
        @NotBlank
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "Date of birth is required")
        String dateOfBirth,

        @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered date is required")
        String registeredDate
) {
}
