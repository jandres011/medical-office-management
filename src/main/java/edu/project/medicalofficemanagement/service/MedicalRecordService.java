package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.MedicalRecordDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicalRecordService {
    MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO);
    List<MedicalRecordDTO> getAllMedicalRecords();
    MedicalRecordDTO getMedicalRecordById(Long id);
    List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long patientId);
    List<MedicalRecordDTO> getMedicalRecordsByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
    MedicalRecordDTO updateMedicalRecord(Long id, MedicalRecordDTO medicalRecordDTO);
    void deleteMedicalRecord(Long id);
}
