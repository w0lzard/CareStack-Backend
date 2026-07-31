package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.AppointmentDto;
import com.ryuken.carestack.entity.Appointment;
import com.ryuken.carestack.entity.AppointmentStatus;
import com.ryuken.carestack.repository.AppointmentRepository;
import com.ryuken.carestack.service.AppointmentReader;
import com.ryuken.carestack.service.AppointmentWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentReader, AppointmentWriter {

    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentDto getAppointmentById(Long id) {
        return AppointmentDto.fromEntity(findOrThrow(id));
    }

    @Override
    public List<AppointmentDto> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findAll().stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .map(AppointmentDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public AppointmentDto bookAppointment(AppointmentDto dto) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(dto.patientId());
        appointment.setDoctorId(dto.doctorId());
        appointment.setScheduledAt(dto.scheduledAt());
        appointment.setReasonForVisit(dto.reasonForVisit());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        Appointment saved = appointmentRepository.save(appointment);
        log.info("Booked appointment {} for patient {}", saved.getId(), saved.getPatientId());
        return AppointmentDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public AppointmentDto cancelAppointment(Long id) {
        Appointment appointment = findOrThrow(id);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        log.info("Cancelled appointment {}", id);
        return AppointmentDto.fromEntity(appointment);
    }

    @Override
    @Transactional
    public AppointmentDto markCompleted(Long id) {
        Appointment appointment = findOrThrow(id);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        log.info("Marked appointment {} completed", id);
        return AppointmentDto.fromEntity(appointment);
    }

    private Appointment findOrThrow(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + id));
    }
}
