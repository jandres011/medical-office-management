package edu.project.medicalofficemanagement.service.impl;

import edu.project.medicalofficemanagement.dto.MedicalRecordDTO;
import edu.project.medicalofficemanagement.exeption.MedicalRecordNotFoundExeption;
import edu.project.medicalofficemanagement.mapper.MedicalRecordMapper;
import edu.project.medicalofficemanagement.model.MedicalRecord;
import edu.project.medicalofficemanagement.repository.MedicalRecordRepository;
import edu.project.medicalofficemanagement.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordImpl implements MedicalRecordService {
    private final MedicalRecordRepository repository;
    private final MedicalRecordMapper mapper;

    @Override
    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = mapper.toEntity(medicalRecordDTO);
        return mapper.toDTO(repository.save(medicalRecord));
    }

    @Override
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public MedicalRecordDTO getMedicalRecordById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new MedicalRecordNotFoundExeption("Medial Record Not found by ID" + id));
    }

    @Override
    public MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO medicalRecordDTO, Long id) {
        return repository.findById(id)
                .map(medicalRecordInDB ->{
                        medicalRecordInDB.setNotes(medicalRecordDTO.getNotes());
                        medicalRecordDTO.setAppointment(medicalRecordDTO.getAppointment());
                        medicalRecordDTO.setDiagnosis(medicalRecordDTO.getDiagnosis());
                        medicalRecordDTO.setPatient(medicalRecordDTO.getPatient());
                    return mapper.toDTO(repository.save(medicalRecordInDB));
                })
                .orElseThrow(() -> new MedicalRecordNotFoundExeption("Medial Record Not found by ID" + id) );
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        if (!repository.existsById(id)) {
            throw new MedicalRecordNotFoundExeption("Medial Record Not found by ID" + id);
        }
        repository.deleteById(id);
    }
}
