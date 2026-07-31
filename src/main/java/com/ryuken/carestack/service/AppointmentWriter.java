package com.ryuken.carestack.service;

import com.ryuken.carestack.dto.AppointmentDto;

import java.util.List;

public interface AppointmentWriter {
    AppointmentDto bookAppointment(AppointmentDto dto);
    AppointmentDto cancelAppointment(Long id);
    AppointmentDto markCompleted(Long id);
}
