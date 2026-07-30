package com.ryuken.carestack.dto;

import com.ryuken.carestack.entity.Doctor;
import com.ryuken.carestack.entity.Specialization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record DoctorDto(
        Long id,
        @NotBlank String fullName,
        @NotNull Specialization specialization,
        @NotBlank String licenseNumber,
        String phoneNumber,
        boolean active
) implements Serializable {
    public static DoctorDto fromEntity(Doctor d) {
        return new DoctorDto(d.getId(), d.getFullName(), d.getSpecialization(),
                d.getLicenseNumber(), d.getPhoneNumber(), d.isActive());
    }

    public Doctor toEntity() {
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setFullName(fullName);
        doctor.setSpecialization(specialization);
        doctor.setLicenseNumber(licenseNumber);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setActive(active);
        return doctor;
    }
}
