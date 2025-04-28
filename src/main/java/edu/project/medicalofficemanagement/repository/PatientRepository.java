package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByPhoneNumber(String phoneNumber);
}
