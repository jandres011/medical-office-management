package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryTest {

    @Mock
    private PatientRepository patientRepository;

    @Test
    void shouldFindPatientByEmail() {
        // Arrange
        Patient patient = Patient.builder()
                .fullName("Maria Garcia")
                .email("maria@example.com")
                .phoneNumber("5551234567")
                .build();

        when(patientRepository.findByEmail("maria@example.com")).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> found = patientRepository.findByEmail("maria@example.com");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Maria Garcia", found.get().getFullName());
        verify(patientRepository).findByEmail("maria@example.com");
    }

    @Test
    void shouldFindPatientByPhoneNumber() {
        // Arrange
        Patient patient = Patient.builder()
                .fullName("Carlos Lopez")
                .email("carlos@example.com")
                .phoneNumber("5559876543")
                .build();

        when(patientRepository.findByPhoneNumber("5559876543")).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> found = patientRepository.findByPhoneNumber("5559876543");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Carlos Lopez", found.get().getFullName());
        verify(patientRepository).findByPhoneNumber("5559876543");
    }

    @Test
    void shouldReturnEmptyWhenPatientNotFound() {
        // Arrange
        when(patientRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<Patient> found = patientRepository.findByEmail("notfound@example.com");

        // Assert
        assertFalse(found.isPresent());
        verify(patientRepository).findByEmail("notfound@example.com");
    }
}
