package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.model.Doctor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorRepositoryTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Test
    void shouldFindDoctorByEmail() {
        // Arrange
        Doctor doctor = Doctor.builder()
                .fullName("Dr. Smith")
                .email("smith@example.com")
                .specialization(Specialization.CARDIOLOGY)
                .availibleFrom(LocalDateTime.now().plusDays(1))
                .availibleTo(LocalDateTime.now().plusDays(2))
                .build();

        when(doctorRepository.findByEmail("smith@example.com")).thenReturn(Optional.of(doctor));

        // Act
        Optional<Doctor> found = doctorRepository.findByEmail("smith@example.com");

        // Assert
        assertTrue(found.isPresent());
        assertEquals(Specialization.CARDIOLOGY, found.get().getSpecialization());
        verify(doctorRepository).findByEmail("smith@example.com");
    }

    @Test
    void shouldFindDoctorsBySpecialization() {
        // Arrange
        Doctor doctor = Doctor.builder()
                .fullName("Dr. Neuro")
                .email("neuro@example.com")
                .specialization(Specialization.NEUROLOGY)
                .availibleFrom(LocalDateTime.now().plusDays(1))
                .availibleTo(LocalDateTime.now().plusDays(2))
                .build();

        List<Doctor> neurologists = List.of(doctor);
        when(doctorRepository.findBySpecialization(Specialization.NEUROLOGY)).thenReturn(neurologists);

        // Act
        List<Doctor> result = doctorRepository.findBySpecialization(Specialization.NEUROLOGY);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("Dr. Neuro", result.get(0).getFullName());
        verify(doctorRepository).findBySpecialization(Specialization.NEUROLOGY);
    }

    @Test
    void shouldFindDoctorsAvailableAtDate() {
        // Arrange
        LocalDateTime testDate = LocalDateTime.now().plusDays(1).withHour(10);

        Doctor doctor = Doctor.builder()
                .fullName("Dr. Available")
                .email("available@example.com")
                .specialization(Specialization.PEDIATRICS)
                .availibleFrom(testDate.minusHours(1))
                .availibleTo(testDate.plusHours(1))
                .build();

        List<Doctor> availableDoctors = List.of(doctor);
        when(doctorRepository.findByAvailibleFromLessThanEqualAndAvailibleToGreaterThanEqual(
                testDate, testDate)).thenReturn(availableDoctors);

        // Act
        List<Doctor> result = doctorRepository.findByAvailibleFromLessThanEqualAndAvailibleToGreaterThanEqual(
                testDate, testDate);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(Specialization.PEDIATRICS, result.get(0).getSpecialization());
        verify(doctorRepository).findByAvailibleFromLessThanEqualAndAvailibleToGreaterThanEqual(
                testDate, testDate);
    }
}
