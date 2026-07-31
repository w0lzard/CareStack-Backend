package com.ryuken.carestack.service;

import com.ryuken.carestack.dto.AppointmentDto;

import java.util.List;

public interface AppointmentReader {
    AppointmentDto getAppointmentById(Long id);
    List<AppointmentDto> getAppointmentsForPatient(Long patientId);
}
