package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    @Test
    void shouldDetectConflictingAppointment() {
        // Arrange
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Smith")
                .specialization(Specialization.CARDIOLOGY)
                .build());

        Patient patient = patientRepository.save(Patient.builder()
                .fullName("John Doe")
                .email("john@example.com")
                .build());

        ConsultRoom room = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 101")
                .floor("First Floor")
                .description("Room Description")
                .build());

        LocalDateTime start = LocalDateTime.now().plusDays(1).withHour(9);
        LocalDateTime end = start.plusHours(1);

        appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(room)
                .startTime(start)
                .endTime(end)
                .build());

        // Act
        List<Appointment> conflicts = appointmentRepository.findConflicts(
                doctor.getId(),
                room.getId(),
                start.plusMinutes(30), // Solapa con la cita existente
                end.plusHours(1)
        );

        // Assert
        assertFalse(conflicts.isEmpty());
    }

    @Test
    void shouldSaveAppointmentWithoutConflict() {
        // Arrange
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Johnson")
                .specialization(Specialization.NEUROLOGY)
                .build());

        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Jane Smith")
                .email("jane@example.com")
                .build());

        ConsultRoom room = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 102")
                .floor("Second Floor")
                .description("Room Description")
                .build());

        LocalDateTime start = LocalDateTime.now().plusDays(2).withHour(14);
        LocalDateTime end = start.plusHours(1);

        // Act
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(room)
                .startTime(start)
                .endTime(end)
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        // Assert
        assertNotNull(saved.getId());
        assertEquals(doctor.getId(), saved.getDoctor().getId());
    }

    @Test
    void shouldFindAppointmentById() {
        // Arrange
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Williams")
                .specialization(Specialization.PEDIATRICS)
                .build());

        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Mike Brown")
                .email("mike@example.com")
                .build());

        ConsultRoom room = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 103")
                .floor("Third Floor")
                .description("Room Description")
                .build());

        LocalDateTime start = LocalDateTime.now().plusDays(3).withHour(11);
        LocalDateTime end = start.plusHours(1);

        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(room)
                .startTime(start)
                .endTime(end)
                .build());

        // Act
        Appointment found = appointmentRepository.findById(appointment.getId()).orElseThrow();

        // Assert
        assertEquals(appointment.getId(), found.getId());
        assertEquals(patient.getId(), found.getPatient().getId());
    }

    @Test
    void shouldDeleteAppointment() {
        // Arrange
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Davis")
                .specialization(Specialization.DERMATOLOGY)
                .build());

        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Sarah Wilson")
                .email("sarah@example.com")
                .build());

        ConsultRoom room = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 104")
                .floor("Second Floor")
                .description("Room Description")
                .build());

        LocalDateTime start = LocalDateTime.now().plusDays(4).withHour(9);
        LocalDateTime end = start.plusHours(1);

        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(room)
                .startTime(start)
                .endTime(end)
                .build());

        Long id = appointment.getId();

        // Act
        appointmentRepository.deleteById(id);

        // Assert
        assertFalse(appointmentRepository.findById(id).isPresent());
    }
}