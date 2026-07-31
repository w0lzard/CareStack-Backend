package com.ryuken.carestack.controller;

import com.ryuken.carestack.dto.AppointmentDto;
import com.ryuken.carestack.service.AppointmentReader;
import com.ryuken.carestack.service.AppointmentWriter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentReader appointmentReader;
    private final AppointmentWriter appointmentWriter;

    @PostMapping
    public ResponseEntity<AppointmentDto> book(@Valid @RequestBody AppointmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentWriter.bookAppointment(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentReader.getAppointmentById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDto>> getForPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentReader.getAppointmentsForPatient(patientId));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<AppointmentDto> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentWriter.cancelAppointment(id));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<AppointmentDto> complete(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentWriter.markCompleted(id));
    }
}
