package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.enums.status.Status;
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
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Smith")
                .email("drsmith@example.com")
                .specialization(Specialization.CARDIOLOGY)
                .availibleFrom(futureDate)
                .availibleTo(futureDate.plusMonths(6))
                .build());

        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Iris Martinez")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .build());

        ConsultRoom room = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 101")
                .floor("Floor 1")
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
                .status(Status.SCHEDULED)
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
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Johnson")
                .email("drjohnson@example.com")
                .specialization(Specialization.NEUROLOGY)
                .availibleFrom(futureDate)
                .availibleTo(futureDate.plusMonths(6))
                .build());

        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Jane Smith")
                .email("jane@example.com")
                .phoneNumber("9876543210")
                .build());

        ConsultRoom room = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 102")
                .floor("Floor 2")
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
                .status(Status.SCHEDULED)
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        // Assert
        assertNotNull(saved.getId());
        assertEquals(doctor.getId(), saved.getDoctor().getId());
    }

    @Test
    void shouldFindAppointmentById() {
        // Arrange
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Williams")
                .email("drwilliams@example.com")
                .specialization(Specialization.PEDIATRICS)
                .availibleFrom(futureDate)
                .availibleTo(futureDate.plusMonths(6))
                .build());

        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Mike Brown")
                .email("mike@example.com")
                .phoneNumber("5551234567")
                .build());

        ConsultRoom room = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 103")
                .floor("Floor 3")
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
                .status(Status.SCHEDULED)
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
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Davis")
                .email("drdavis@example.com")
                .specialization(Specialization.DERMATOLOGY)
                .availibleFrom(futureDate)
                .availibleTo(futureDate.plusMonths(6))
                .build());

        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Sarah Wilson")
                .email("sarah@example.com")
                .phoneNumber("7778889999")
                .build());

        ConsultRoom room = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 104")
                .floor("Floor 4")
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
                .status(Status.SCHEDULED)
                .build());

        Long id = appointment.getId();

        // Act
        appointmentRepository.deleteById(id);

        // Assert
        assertFalse(appointmentRepository.findById(id).isPresent());
    }
}
