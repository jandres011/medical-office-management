package edu.project.medicalofficemanagement.dto.mapper;

import edu.project.medicalofficemanagement.dto.DoctorDTO;
import edu.project.medicalofficemanagement.model.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDTO(Doctor doctor);
    Doctor toEntity(DoctorDTO doctorDTO);
}
