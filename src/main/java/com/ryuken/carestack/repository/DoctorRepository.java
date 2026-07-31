package com.ryuken.carestack.repository;

import com.ryuken.carestack.entity.Doctor;
import com.ryuken.carestack.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecializationAndActiveTrue(Specialization specialization);
}

