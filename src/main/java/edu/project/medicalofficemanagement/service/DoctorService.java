package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.DoctorDTO;
import edu.project.medicalofficemanagement.enums.specialization.Specialization;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorService {
    DoctorDTO createDoctor(DoctorDTO doctorDTO);
    List<DoctorDTO> getAllDoctors();
    DoctorDTO getDoctorById(Long id);
    DoctorDTO getDoctorByEmail(String email);
    List<DoctorDTO> getDoctorsBySpecialization(Specialization specialization);
    List<DoctorDTO> getDoctorsByAvailibleFrom(LocalDateTime availibleFrom);
    DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO);
    void deleteDoctor(Long id);
}
