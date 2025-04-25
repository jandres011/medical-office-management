package edu.project.medicalofficemanagement.controller;

import edu.project.medicalofficemanagement.dto.PatientDTO;
import edu.project.medicalofficemanagement.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientDTO));
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientById(id));
    }

    @GetMapping("/{email}")
    public ResponseEntity<PatientDTO> getPatientByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO patientDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.updatePatient(id,patientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatientDTO> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
