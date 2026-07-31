package com.ryuken.carestack.service;

import com.ryuken.carestack.dto.MedicalRecordDto;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecordDto createRecord(MedicalRecordDto dto);
    List<MedicalRecordDto> getRecordsForPatient(Long patientId);
}
