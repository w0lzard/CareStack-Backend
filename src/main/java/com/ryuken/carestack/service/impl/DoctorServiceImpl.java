package com.ryuken.carestack.service.impl;

import com.ryuken.carestack.dto.DoctorDto;
import com.ryuken.carestack.entity.Doctor;
import com.ryuken.carestack.entity.Specialization;
import com.ryuken.carestack.repository.DoctorRepository;
import com.ryuken.carestack.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public DoctorDto createDoctor(DoctorDto dto) {
        Doctor saved = doctorRepository.save(dto.toEntity());
        log.info("Created doctor {}", saved.getId());
        return DoctorDto.fromEntity(saved);
    }

    @Override
    @Cacheable(value = "doctors", key = "#id")
    public DoctorDto getDoctorById(Long id) {
        log.debug("Cache miss - loading doctor {} from database", id);
        return DoctorDto.fromEntity(findOrThrow(id));
    }

    @Override
    public List<DoctorDto> getDoctorsBySpecialization(Specialization specialization) {
        return doctorRepository.findBySpecializationAndActiveTrue(specialization).stream()
                .map(DoctorDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    @CachePut(value = "doctors", key = "#id")
    public DoctorDto updateDoctor(Long id, DoctorDto dto) {
        Doctor existing = findOrThrow(id);
        existing.setFullName(dto.fullName());
        existing.setSpecialization(dto.specialization());
        existing.setPhoneNumber(dto.phoneNumber());
        log.info("Updated doctor {} - cache entry refreshed", id);
        return DoctorDto.fromEntity(existing);
    }

    @Override
    @Transactional
    @CacheEvict(value = "doctors", key = "#id")
    public void deactivateDoctor(Long id) {
        Doctor existing = findOrThrow(id);
        existing.setActive(false);
        log.info("Deactivated doctor {} - cache entry evicted", id);
    }

    private Doctor findOrThrow(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + id));
    }
}
