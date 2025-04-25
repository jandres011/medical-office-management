package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class PatientRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldFindPatientByEmail() {
        patientRepository.save(Patient.builder()
                .fullName("Maria Garcia")
                .email("maria@example.com")
                .phoneNumber("5551234567")
                .build());

        Optional<Patient> found = patientRepository.findByEmail("maria@example.com");

        assertTrue(found.isPresent());
        assertEquals("Maria Garcia", found.get().getFullName());
    }

    @Test
    void shouldFindPatientByPhoneNumber() {
        patientRepository.save(Patient.builder()
                .fullName("Carlos Lopez")
                .email("carlos@example.com")
                .phoneNumber("5559876543")
                .build());

        Optional<Patient> found = patientRepository.findByPhoneNumber("5559876543");

        assertTrue(found.isPresent());
        assertEquals("Carlos Lopez", found.get().getFullName());
    }

    @Test
    void shouldReturnEmptyWhenPatientNotFound() {
        Optional<Patient> found = patientRepository.findByEmail("notfound@example.com");
        assertFalse(found.isPresent());
    }
}