package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.MedicalRecordDTO;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO);
    List<MedicalRecordDTO> getAllMedicalRecords();
    MedicalRecordDTO getMedicalRecordById(Long id);
    MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO medicalRecordDTO, Long id);
    void deleteMedicalRecord(Long id);
}
