package edu.project.medicalofficemanagement.service.impl;

import edu.project.medicalofficemanagement.dto.MedicalRecordDTO;
import edu.project.medicalofficemanagement.exception.AppointmentNotFoundException;
import edu.project.medicalofficemanagement.exception.MedicalRecordNotFoundException;
import edu.project.medicalofficemanagement.dto.mapper.MedicalRecordMapper;
import edu.project.medicalofficemanagement.exception.PatientNotFoundException;
import edu.project.medicalofficemanagement.model.Appointment;
import edu.project.medicalofficemanagement.model.MedicalRecord;
import edu.project.medicalofficemanagement.model.Patient;
import edu.project.medicalofficemanagement.repository.AppointmentRepository;
import edu.project.medicalofficemanagement.repository.MedicalRecordRepository;
import edu.project.medicalofficemanagement.repository.PatientRepository;
import edu.project.medicalofficemanagement.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    @Override
    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        Appointment appointment = appointmentRepository.findById(medicalRecordDTO.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID:" + medicalRecordDTO.getAppointmentId()));

        Patient patient = patientRepository.findById(medicalRecordDTO.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID:" + medicalRecordDTO.getPatientId()));

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(medicalRecordDTO);
        medicalRecord.setAppointment(appointment);
        medicalRecord.setPatient(patient);

        return medicalRecordMapper.toDTO(medicalRecordRepository.save(medicalRecord));
    }

    @Override
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return medicalRecordRepository.findAll()
                .stream().map(medicalRecordMapper::toDTO)
                .toList();
    }

    @Override
    public MedicalRecordDTO getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .map(medicalRecordMapper::toDTO)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medial Record Not found by ID" + id));
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long patientId) {
        if(!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException("Patient not found with ID:" + patientId);
        }

        return medicalRecordRepository.findMedicalRecordsByPatient_Id(patientId)
                .orElseThrow(() -> new MedicalRecordNotFoundException("No medical records were found for that patient with ID: " + patientId))
                .stream()
                .map(medicalRecordMapper::toDTO)
                .toList();
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        return medicalRecordRepository.findMedicalRecordsByCreatedAtBetween(from, to)
                .orElseThrow(() -> new MedicalRecordNotFoundException("No medical records created in this date range were found:" + from + " - " + to))
                .stream()
                .map(medicalRecordMapper::toDTO)
                .toList();
    }

    @Override
    public MedicalRecordDTO updateMedicalRecord(Long id, MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medial Record Not found by ID" + id));

        Appointment appointment = appointmentRepository.findById(medicalRecordDTO.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID:" + medicalRecordDTO.getAppointmentId()));

        Patient patient = patientRepository.findById(medicalRecordDTO.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID:" + medicalRecordDTO.getPatientId()));

        medicalRecord.setDiagnosis(medicalRecordDTO.getDiagnosis());
        medicalRecord.setNotes(medicalRecordDTO.getNotes());
        medicalRecord.setAppointment(appointment);
        medicalRecord.setPatient(patient);

        return medicalRecordMapper.toDTO(medicalRecordRepository.save(medicalRecord));
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new MedicalRecordNotFoundException("Medial Record Not found by ID" + id);
        }
        medicalRecordRepository.deleteById(id);
    }
}
