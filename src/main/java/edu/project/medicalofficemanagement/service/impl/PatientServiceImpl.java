package edu.project.medicalofficemanagement.service.impl;

import edu.project.medicalofficemanagement.dto.PatientDTO;
import edu.project.medicalofficemanagement.dto.mapper.PatientMapper;
import edu.project.medicalofficemanagement.exception.PatientNotFoundException;
import edu.project.medicalofficemanagement.model.Patient;
import edu.project.medicalofficemanagement.repository.PatientRepository;
import edu.project.medicalofficemanagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        return patientMapper.toDTO(patientRepository.save(patient));
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDTO)
                .toList();
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new PatientNotFoundException("Patient Not found by ID" + id));
    }

    @Override
    public PatientDTO getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with email: " + email));
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        return patientRepository.findById(id)
                .map(patientInDB -> {
                    patientInDB.setFullName(patientDTO.getFullName());
                    patientInDB.setEmail(patientDTO.getEmail());
                    patientInDB.setPhoneNumber(patientDTO.getPhoneNumber());
                    return patientMapper.toDTO(patientRepository.save(patientInDB));
                })
                .orElseThrow(() -> new PatientNotFoundException("Patient Not found by ID" + id));
    }

    @Override
    public void deletePatient(Long id) {
        if(!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient Not found by ID" + id);
        }
        patientRepository.deleteById(id);
    }
}
