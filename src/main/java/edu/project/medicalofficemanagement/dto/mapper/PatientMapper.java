package edu.project.medicalofficemanagement.dto.mapper;

import edu.project.medicalofficemanagement.dto.PatientDTO;
import edu.project.medicalofficemanagement.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDTO(Patient patient);
    Patient toEntity(PatientDTO patientDTO);
}
