package com.ryuken.carestack.repository;

import com.ryuken.carestack.entity.Appointment;
import com.ryuken.carestack.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByScheduledAtBetweenAndStatus(
            LocalDateTime start, LocalDateTime end, AppointmentStatus status);
}
