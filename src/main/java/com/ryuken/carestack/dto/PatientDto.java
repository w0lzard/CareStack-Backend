package com.ryuken.carestack.dto;

import com.ryuken.carestack.entity.Patient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PatientDto(
        Long id,
        @NotBlank String fullName,
        @NotNull @Past LocalDate dateOfBirth,
        @NotBlank String phoneNumber,
        String email,
        String bloodGroup,
        String address
) {
    public static PatientDto fromEntity(Patient p) {
        return new PatientDto(p.getId(), p.getFullName(), p.getDateOfBirth(),
                p.getPhoneNumber(), p.getEmail(), p.getBloodGroup(), p.getAddress());
    }

    public Patient toEntity() {
        return new Patient(id, fullName, dateOfBirth, phoneNumber, email, bloodGroup, address);
    }
}
