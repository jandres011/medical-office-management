package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findDoctorByEmail(String email);
    Optional<List<Doctor>> findDoctorsBySpecialization(Specialization specialization);
    Optional<List<Doctor>> findDoctorsByAvailibleFrom(LocalDateTime availibleFrom);

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findBySpecialization(Specialization specialization);

    List<Doctor> findByAvailibleFromLessThanEqualAndAvailibleToGreaterThanEqual(LocalDateTime availibleFromIsLessThan, LocalDateTime availibleToIsGreaterThan);
}
