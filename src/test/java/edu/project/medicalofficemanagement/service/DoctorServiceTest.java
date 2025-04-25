package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.DoctorDTO;
import edu.project.medicalofficemanagement.dto.mapper.DoctorMapper;
import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.exception.DoctorNotFoundException;
import edu.project.medicalofficemanagement.model.Doctor;
import edu.project.medicalofficemanagement.repository.DoctorRepository;
import edu.project.medicalofficemanagement.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private Doctor doctor;
    private DoctorDTO doctorDTO;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFullName("Dr. Smith");
        doctor.setSpecialization(Specialization.CARDIOLOGY);
        doctor.setEmail("smith@example.com");

        doctorDTO = new DoctorDTO();
        doctorDTO.setFullName("Dr. Smith");
        doctorDTO.setSpecialization(Specialization.CARDIOLOGY);
        doctorDTO.setEmail("smith@example.com");
    }

    @Test
    void createDoctor_ShouldSuccess() {
        when(doctorMapper.toEntity(doctorDTO)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.createDoctor(doctorDTO);

        assertNotNull(result);
        assertEquals(Specialization.CARDIOLOGY, result.getSpecialization());
    }

    @Test
    void getDoctorsBySpecialization_ShouldReturnList() {
        when(doctorRepository.findBySpecialization(Specialization.CARDIOLOGY))
                .thenReturn(List.of(doctor));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        List<DoctorDTO> result = doctorService.getDoctorsBySpecialization(Specialization.CARDIOLOGY);

        assertFalse(result.isEmpty());
        assertEquals("Dr. Smith", result.get(0).getFullName());
    }

    @Test
    void getDoctorByEmail_ShouldReturnDoctor() {
        when(doctorRepository.findByEmail("smith@example.com")).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.getDoctorByEmail("smith@example.com");

        assertNotNull(result);
        assertEquals("smith@example.com", result.getEmail());
    }

    @Test
    void updateDoctor_ShouldUpdateSuccessfully() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.updateDoctor(1L, doctorDTO);

        assertNotNull(result);
        verify(doctorRepository, times(1)).save(doctor);
    }
}