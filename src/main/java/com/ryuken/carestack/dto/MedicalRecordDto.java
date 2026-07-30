package com.ryuken.carestack.dto;

import com.ryuken.carestack.entity.MedicalRecord;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record MedicalRecordDto(
        Long id,
        @NotNull Long patientId,
        @NotNull Long appointmentId,
        @NotBlank String diagnosis,
        String notes,
        Instant recordedAt
) {
    public static MedicalRecordDto fromEntity(MedicalRecord r) {
        return new MedicalRecordDto(r.getId(), r.getPatientId(), r.getAppointmentId(),
                r.getDiagnosis(), r.getNotes(), r.getRecordedAt());
    }
}
