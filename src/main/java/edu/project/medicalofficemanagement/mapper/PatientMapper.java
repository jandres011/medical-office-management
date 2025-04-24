package edu.project.medicalofficemanagement.mapper;

import edu.project.medicalofficemanagement.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientMapper toDTO(Patient patient);
    Patient toEntity(PatientMapper patientMapper);
}
