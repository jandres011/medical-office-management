package edu.project.medicalofficemanagement.controller;

import edu.project.medicalofficemanagement.dto.DoctorDTO;
import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(doctorDTO));
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getDoctorById(id));
    }

    @GetMapping("/{email}")
    public ResponseEntity<DoctorDTO> getDoctorByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getDoctorByEmail(email));
    }

    @GetMapping("/{specialization}")
    public ResponseEntity<List<DoctorDTO>> getDoctorBySpecialization(@PathVariable Specialization specialization) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getDoctorsBySpecialization(specialization));
    }

    @GetMapping("/{availibleFrom}")
    public ResponseEntity<List<DoctorDTO>> getDoctorByAvailibleFrom(@RequestParam LocalDateTime availibleFrom) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getDoctorsByAvailibleFrom(availibleFrom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.updateDoctor(id, doctorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DoctorDTO> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
