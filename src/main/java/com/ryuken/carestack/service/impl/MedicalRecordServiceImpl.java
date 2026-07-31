package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.MedicalRecordDto;
import com.ryuken.carestack.entity.MedicalRecord;
import com.ryuken.carestack.repository.MedicalRecordRepository;
import com.ryuken.carestack.service.AppointmentReader;
import com.ryuken.carestack.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentReader appointmentReader;


    @Override
    @Transactional
    public MedicalRecordDto createRecord(MedicalRecordDto dto) {
        // Validates the referenced appointment actually exists before attaching a record to it.
        var appointment = appointmentReader.getAppointmentById(dto.appointmentId());
        if (!appointment.patientId().equals(dto.patientId())) {
            throw new ResourceNotFoundException(
                    "Appointment " + dto.appointmentId() + " does not belong to patient " + dto.patientId());
        }

        MedicalRecord record = new MedicalRecord();
        record.setPatientId(dto.patientId());
        record.setAppointmentId(dto.appointmentId());
        record.setDiagnosis(dto.diagnosis());
        record.setNotes(dto.notes());
        record.setRecordedAt(Instant.now());
        MedicalRecord saved = medicalRecordRepository.save(record);
        log.info("Created medical record {} for patient {}", saved.getId(), saved.getPatientId());
        return MedicalRecordDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public List<MedicalRecordDto> getRecordsForPatient(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId).stream()
                .map(MedicalRecordDto::fromEntity)
                .toList();
    }
}
