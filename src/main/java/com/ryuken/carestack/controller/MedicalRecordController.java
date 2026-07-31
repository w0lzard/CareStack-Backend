package com.ryuken.carestack.controller;

import com.ryuken.carestack.dto.MedicalRecordDto;
import com.ryuken.carestack.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordDto> create(@Valid @RequestBody MedicalRecordDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.createRecord(dto));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordDto>> getForPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalRecordService.getRecordsForPatient(patientId));
    }
}
