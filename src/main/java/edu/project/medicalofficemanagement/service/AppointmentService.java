package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO getAppointmentById(Long id);
    AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO);
    void deleteAppointment(Long id);
}
