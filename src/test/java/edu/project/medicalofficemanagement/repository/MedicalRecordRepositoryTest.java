package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class MedicalRecordRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldFindMedicalRecordsByPatientId() {
        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Juancho Lopez")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .build());

        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .build());

        medicalRecordRepository.save(MedicalRecord.builder()
                .patient(patient)
                .appointment(appointment)
                .diagnosis("Common cold")
                .notes("Prescribed rest")
                .build());

        List<MedicalRecord> records = medicalRecordRepository.findByPatient_Id((patient.getId()));

        assertFalse(records.isEmpty());
        assertEquals("Common cold", records.get(0).getDiagnosis());
    }

    @Test
    void shouldFindMedicalRecordsByDateRange() {
        Patient patient = patientRepository.save(Patient.builder()
                .fullName("Pinco Lopez")
                .email("jane@example.com")
                .phoneNumber("0987654321")
                .build());

        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .patient(patient)
                .build());

        MedicalRecord record = medicalRecordRepository.save(MedicalRecord.builder()
                .patient(patient)
                .appointment(appointment)
                .diagnosis("Fever")
                .notes("Prescribed medication")
                .build());

        record.setCreatedAt(LocalDateTime.now().minusDays(1));
        medicalRecordRepository.save(record);

        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now();

        List<MedicalRecord> records = medicalRecordRepository.findByCreatedAtBetween(start, end);

        assertFalse(records.isEmpty());
        assertEquals("Fever", records.get(0).getDiagnosis());
    }
}