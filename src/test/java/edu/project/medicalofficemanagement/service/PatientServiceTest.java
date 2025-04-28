package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.PatientDTO;
import edu.project.medicalofficemanagement.dto.mapper.PatientMapper;
import edu.project.medicalofficemanagement.exception.PatientNotFoundException;
import edu.project.medicalofficemanagement.model.Patient;
import edu.project.medicalofficemanagement.repository.PatientRepository;
import edu.project.medicalofficemanagement.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;
    private PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setFullName("John Doe");
        patient.setEmail("john@example.com");
        patient.setPhoneNumber("1234567890");

        patientDTO = new PatientDTO();
        patientDTO.setFullName("John Doe");
        patientDTO.setEmail("john@example.com");
        patientDTO.setPhoneNumber("1234567890");
    }

    @Test
    void createPatient_ShouldSuccess() {
        when(patientMapper.toEntity(patientDTO)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

        PatientDTO result = patientService.createPatient(patientDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void getPatientById_ShouldReturnPatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

        PatientDTO result = patientService.getPatientById(1L);

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void getPatientById_ShouldThrowWhenNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            patientService.getPatientById(1L);
        });
    }

    @Test
    void getPatientByEmail_ShouldReturnPatient() {
        when(patientRepository.findByEmail("john@example.com")).thenReturn(Optional.of(patient));
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

        PatientDTO result = patientService.getPatientByEmail("john@example.com");

        assertNotNull(result);
        assertEquals("1234567890", result.getPhoneNumber());
    }

    @Test
    void updatePatient_ShouldUpdateSuccessfully() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(patientDTO);

        PatientDTO result = patientService.updatePatient(1L, patientDTO);

        assertNotNull(result);
        verify(patientRepository, times(1)).save(patient);
    }
}
