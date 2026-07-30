package com.ryuken.carestack.dto;

import com.ryuken.carestack.entity.Appointment;
import com.ryuken.carestack.entity.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentDto(
        Long id,
        @NotNull Long patientId,
        @NotNull Long doctorId,
        @NotNull @Future LocalDateTime scheduledAt,
        AppointmentStatus status,
        String reasonForVisit
) {
    public static AppointmentDto fromEntity(Appointment a) {
        return new AppointmentDto(a.getId(), a.getPatientId(), a.getDoctorId(),
                a.getScheduledAt(), a.getStatus(), a.getReasonForVisit());
    }
}
