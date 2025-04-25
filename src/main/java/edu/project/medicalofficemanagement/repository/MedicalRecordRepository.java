package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<List<MedicalRecord>> findMedicalRecordsByPatient_Id(Long patientId);
    Optional<List<MedicalRecord>> findMedicalRecordsByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    List<MedicalRecord> findByPatient_Id(Long patientId);

    List<MedicalRecord> findByCreatedAtBetween(LocalDateTime createdAtAfter, LocalDateTime createdAtBefore);
}
