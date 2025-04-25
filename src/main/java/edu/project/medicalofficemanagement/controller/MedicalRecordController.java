package edu.project.medicalofficemanagement.controller;

import edu.project.medicalofficemanagement.dto.MedicalRecordDTO;
import edu.project.medicalofficemanagement.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medicalRecords")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@Valid @RequestBody MedicalRecordDTO medicalRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.createMedicalRecord(medicalRecordDTO));
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.getAllMedicalRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecordById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.getMedicalRecordById(id));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.getMedicalRecordsByPatientId(patientId));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime to) {

        return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.getMedicalRecordsByCreatedAtBetween(from, to));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@PathVariable Long id, @Valid @RequestBody MedicalRecordDTO medicalRecordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(medicalRecordService.updateMedicalRecord(id, medicalRecordDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
