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
class MedicalRecordRepositoryTest {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    @Test
    void shouldFindMedicalRecordsByPatientId() {
        // Create a patient
        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Juancho Lopez")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .build());

        // Create a doctor
        LocalDateTime availableFrom = LocalDateTime.now().plusDays(1);
        LocalDateTime availableTo = availableFrom.plusDays(30);
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Smith")
                .email("smith@example.com")
                .specialization(Specialization.CARDIOLOGY)
                .availibleFrom(availableFrom)
                .availibleTo(availableTo)
                .build());

        // Create a consult room
        ConsultRoom consultRoom = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 101")
                .floor("1")
                .description("Cardiology consultation room")
                .build());

        // Create appointment with all required fields
        LocalDateTime startTime = LocalDateTime.now().plusDays(2);
        LocalDateTime endTime = startTime.plusHours(1);
        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(consultRoom)
                .startTime(startTime)
                .endTime(endTime)
                .status(Status.SCHEDULED)
                .build());

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient)
                .appointment(appointment)
                .diagnosis("Common cold")
                .notes("Prescribed rest")
                .createdAt(LocalDateTime.now())
                .build();
        medicalRecordRepository.save(medicalRecord);

        List<MedicalRecord> records = medicalRecordRepository.findByPatient_Id((patient.getId()));

        assertFalse(records.isEmpty());
        assertEquals("Common cold", records.get(0).getDiagnosis());
    }

    @Test
    void shouldFindMedicalRecordsByDateRange() {
        // Create a patient
        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Pinco Lopez")
                .email("jane@example.com")
                .phoneNumber("0987654321")
                .build());

        // Create a doctor
        LocalDateTime availableFrom = LocalDateTime.now().plusDays(1);
        LocalDateTime availableTo = availableFrom.plusDays(30);
        Doctor doctor = doctorRepository.save(Doctor.builder()
                .fullName("Dr. Johnson")
                .email("johnson@example.com")
                .specialization(Specialization.PEDIATRICS)
                .availibleFrom(availableFrom)
                .availibleTo(availableTo)
                .build());

        // Create a consult room
        ConsultRoom consultRoom = consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 202")
                .floor("2")
                .description("Pediatrics consultation room")
                .build());

        // Create appointment with all required fields
        LocalDateTime startTime = LocalDateTime.now().plusDays(3);
        LocalDateTime endTime = startTime.plusHours(1);
        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .consultRoom(consultRoom)
                .startTime(startTime)
                .endTime(endTime)
                .status(Status.SCHEDULED)
                .build());

        MedicalRecord record = MedicalRecord.builder()
                .patient(patient)
                .appointment(appointment)
                .diagnosis("Fever")
                .notes("Prescribed medication")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
        medicalRecordRepository.save(record);

        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now();

        List<MedicalRecord> records = medicalRecordRepository.findByCreatedAtBetween(start, end);

        assertFalse(records.isEmpty());
        assertEquals("Fever", records.get(0).getDiagnosis());
    }
}
