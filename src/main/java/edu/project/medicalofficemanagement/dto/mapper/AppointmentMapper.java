package edu.project.medicalofficemanagement.dto.mapper;

import edu.project.medicalofficemanagement.dto.AppointmentDTO;
import edu.project.medicalofficemanagement.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppointmentMapper {
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "consultRoom.id", target = "consultRoomId")
    AppointmentDTO toDTO(Appointment appointment);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "consultRoom", ignore = true)
    Appointment toEntity(AppointmentDTO dto);
}