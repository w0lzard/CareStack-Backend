package com.ryuken.carestack.dto;

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
}
