package com.ryuken.carestack.service;

import com.ryuken.carestack.dto.DoctorDto;
import com.ryuken.carestack.entity.Specialization;

import java.util.List;

public interface DoctorService {
    DoctorDto createDoctor(DoctorDto dto);
    DoctorDto getDoctorById(Long id);
    List<DoctorDto> getDoctorsBySpecialization(Specialization specialization);
    DoctorDto updateDoctor(Long id, DoctorDto dto);
    void deactivateDoctor(Long id);
}
