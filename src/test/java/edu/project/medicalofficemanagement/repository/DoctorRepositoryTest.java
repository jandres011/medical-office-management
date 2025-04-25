package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.model.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class DoctorRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");
    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void shouldFindDoctorByEmail() {
        doctorRepository.save(Doctor.builder()
                .fullName("Dr. Smith")
                .email("smith@example.com")
                .specialization(Specialization.CARDIOLOGY)
                .availibleFrom(LocalDateTime.now().plusDays(1))
                .availibleTo(LocalDateTime.now().plusDays(2))
                .build());

        Optional<Doctor> found = doctorRepository.findByEmail(("smith@example.com"));

        assertTrue(found.isPresent());
        assertEquals(Specialization.CARDIOLOGY, found.get().getSpecialization());
    }

    @Test
    void shouldFindDoctorsBySpecialization() {
        doctorRepository.save(Doctor.builder()
                .fullName("Dr. Neuro")
                .email("neuro@example.com")
                .specialization(Specialization.NEUROLOGY)
                .availibleFrom(LocalDateTime.now().plusDays(1))
                .availibleTo(LocalDateTime.now().plusDays(2))
                .build());

        List<Doctor> neurologists = doctorRepository.findBySpecialization((Specialization.NEUROLOGY));

        assertFalse(neurologists.isEmpty());
        assertEquals("Dr. Neuro", neurologists.get(0).getFullName());
    }

    @Test
    void shouldFindDoctorsAvailableAtDate() {
        LocalDateTime testDate = LocalDateTime.now().plusDays(1).withHour(10);

        doctorRepository.save(Doctor.builder()
                .fullName("Dr. Available")
                .email("available@example.com")
                .specialization(Specialization.PEDIATRICS)
                .availibleFrom(testDate.minusHours(1))
                .availibleTo(testDate.plusHours(1))
                .build());

        List<Doctor> availableDoctors = doctorRepository.findByAvailibleFromLessThanEqualAndAvailibleToGreaterThanEqual(
                testDate, testDate);

        assertFalse(availableDoctors.isEmpty());
        assertEquals(Specialization.PEDIATRICS, availableDoctors.get(0).getSpecialization());
    }
}