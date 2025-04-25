package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDTO);
    List<PatientDTO> getAllPatients();
    PatientDTO getPatientById(Long id);
    PatientDTO getPatientByEmail(String email);
    PatientDTO updatePatient(Long id, PatientDTO patientDTO);
    void deletePatient(Long id);
}
