package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.MedicalRecordDTO;
import edu.project.medicalofficemanagement.dto.mapper.MedicalRecordMapper;
import edu.project.medicalofficemanagement.exception.*;
import edu.project.medicalofficemanagement.model.*;
import edu.project.medicalofficemanagement.repository.*;
import edu.project.medicalofficemanagement.service.impl.MedicalRecordImpl;
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
class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private MedicalRecordImpl medicalRecordService;

    private MedicalRecord medicalRecord;
    private MedicalRecordDTO medicalRecordDTO;
    private Appointment appointment;
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatient(patient);

        medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setAppointment(appointment);
        medicalRecord.setPatient(patient);
        medicalRecord.setDiagnosis("Common cold");
        medicalRecord.setNotes("Rest for 3 days");

        medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setAppointmentId(1L);
        medicalRecordDTO.setPatientId(1L);
        medicalRecordDTO.setDiagnosis("Common cold");
        medicalRecordDTO.setNotes("Rest for 3 days");
    }

    @Test
    void createMedicalRecord_ShouldSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(medicalRecordMapper.toEntity(medicalRecordDTO)).thenReturn(medicalRecord);
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(medicalRecordDTO);

        MedicalRecordDTO result = medicalRecordService.createMedicalRecord(medicalRecordDTO);

        assertNotNull(result);
        assertEquals("Common cold", result.getDiagnosis());
    }

    @Test
    void getMedicalRecordsByPatientId_ShouldReturnList() {
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(medicalRecordRepository.findMedicalRecordsByPatient_Id(1L)).thenReturn(Optional.of(List.of(medicalRecord)));
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(medicalRecordDTO);

        List<MedicalRecordDTO> result = medicalRecordService.getMedicalRecordsByPatientId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getMedicalRecordById_ShouldReturnRecord() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(medicalRecordDTO);

        MedicalRecordDTO result = medicalRecordService.getMedicalRecordById(1L);

        assertNotNull(result);
        assertEquals("Rest for 3 days", result.getNotes());
    }

    @Test
    void updateMedicalRecord_ShouldUpdateSuccessfully() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(medicalRecordDTO);

        MedicalRecordDTO result = medicalRecordService.updateMedicalRecord(1L, medicalRecordDTO);

        assertNotNull(result);
        verify(medicalRecordRepository, times(1)).save(medicalRecord);
    }
}
