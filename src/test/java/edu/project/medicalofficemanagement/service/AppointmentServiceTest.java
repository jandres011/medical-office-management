package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.AppointmentDTO;
import edu.project.medicalofficemanagement.dto.mapper.AppointmentMapper;
import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.exception.*;
import edu.project.medicalofficemanagement.model.*;
import edu.project.medicalofficemanagement.repository.*;
import edu.project.medicalofficemanagement.service.impl.AppointmentServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ConsultRoomRepository consultRoomRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private AppointmentDTO appointmentDTO;
    private Appointment appointment;
    private Patient patient;
    private Doctor doctor;
    private ConsultRoom consultRoom;

    @BeforeEach
    void setUp() {
        appointmentDTO = new AppointmentDTO();
        appointmentDTO.setPatientId(1L);
        appointmentDTO.setDoctorId(1L);
        appointmentDTO.setConsultRoomId(1L);
        appointmentDTO.setStartTime(LocalDateTime.now().plusDays(1));
        appointmentDTO.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));

        patient = new Patient();
        patient.setId(1L);

        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setSpecialization(Specialization.CARDIOLOGY);

        consultRoom = new ConsultRoom();
        consultRoom.setId(1L);

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setConsultRoom(consultRoom);
    }

    @Test
    void createAppointment_ShouldSuccess() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.of(consultRoom));
        when(appointmentRepository.findConflicts(any(), any(), any(), any())).thenReturn(List.of());
        when(appointmentMapper.toEntity(any())).thenReturn(appointment);
        when(appointmentRepository.save(any())).thenReturn(appointment);
        when(appointmentMapper.toDTO(any())).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.createAppointment(appointmentDTO);

        assertNotNull(result);
        verify(appointmentRepository, times(1)).save(any());
    }

    @Test
    void createAppointment_ShouldThrowWhenPatientNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> {
            appointmentService.createAppointment(appointmentDTO);
        });
    }

    @Test
    void createAppointment_ShouldThrowWhenConflictExists() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.of(consultRoom));
        when(appointmentRepository.findConflicts(any(), any(), any(), any())).thenReturn(List.of(appointment));

        assertThrows(AppointmentAlreadyExistException.class, () -> {
            appointmentService.createAppointment(appointmentDTO);
        });
    }

    @Test
    void getAppointmentById_ShouldReturnAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentMapper.toDTO(any())).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.getAppointmentById(1L);

        assertNotNull(result);
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void getAppointmentById_ShouldThrowWhenNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.getAppointmentById(1L);
        });
    }

    @Test
    void updateAppointment_ShouldUpdateSuccessfully() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.of(consultRoom));
        when(appointmentRepository.findConflicts(any(), any(), any(), any())).thenReturn(List.of());
        when(appointmentRepository.save(any())).thenReturn(appointment);
        when(appointmentMapper.toDTO(any())).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.updateAppointment(1L, appointmentDTO);

        assertNotNull(result);
        verify(appointmentRepository, times(1)).save(any());
    }

    @Test
    void deleteAppointment_ShouldDeleteSuccessfully() {
        when(appointmentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(appointmentRepository).deleteById(1L);

        assertDoesNotThrow(() -> {
            appointmentService.deleteAppointment(1L);
        });

        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}