package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.PatientDto;
import com.ryuken.carestack.entity.Patient;
import com.ryuken.carestack.repository.PatientRepository;
import com.ryuken.carestack.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public PatientDto createPatient(PatientDto dto) {
        Patient saved = patientRepository.save(dto.toEntity());
        log.info("Created patient {}", saved.getId());
        return PatientDto.fromEntity(saved);
    }

    @Override
    public PatientDto getPatientById(Long id) {
        return PatientDto.fromEntity(findOrThrow(id));
    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll().stream().map(PatientDto::fromEntity).toList();
    }

    @Override
    @Transactional
    public PatientDto updatePatient(Long id, PatientDto dto) {
        Patient existing = findOrThrow(id);
        existing.setFullName(dto.fullName());
        existing.setDateOfBirth(dto.dateOfBirth());
        existing.setPhoneNumber(dto.phoneNumber());
        existing.setEmail(dto.email());
        existing.setBloodGroup(dto.bloodGroup());
        existing.setAddress(dto.address());
        log.info("Updated patient {}", id);
        return PatientDto.fromEntity(existing);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        findOrThrow(id);
        patientRepository.deleteById(id);
        log.info("Deleted patient {}", id);
    }

    private Patient findOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + id));
    }
}
