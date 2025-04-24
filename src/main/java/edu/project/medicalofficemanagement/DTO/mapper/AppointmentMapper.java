package edu.project.medicalofficemanagement.DTO.mapper;

import edu.project.medicalofficemanagement.DTO.AppointmentDto;
import edu.project.medicalofficemanagement.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(source = "patient.fullname", target = "patientName")
    @Mapping(source = "doctor.fullname", target = "doctorName")
    AppointmentDto toDto(Appointment appointment);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    Appointment toEntity(AppointmentDto dto);
}
