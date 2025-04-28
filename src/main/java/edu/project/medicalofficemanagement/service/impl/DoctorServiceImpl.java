package edu.project.medicalofficemanagement.service.impl;

import edu.project.medicalofficemanagement.dto.DoctorDTO;
import edu.project.medicalofficemanagement.dto.mapper.DoctorMapper;
import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.exception.DoctorNotFoundException;
import edu.project.medicalofficemanagement.model.Doctor;
import edu.project.medicalofficemanagement.repository.DoctorRepository;
import edu.project.medicalofficemanagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        return doctorMapper.toDTO(doctorRepository.save(doctor));
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + id));
    }

    @Override
    public DoctorDTO getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email)
                .map(doctorMapper::toDTO)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with email: " + email));
    }

    @Override
    public List<DoctorDTO> getDoctorsBySpecialization(Specialization specialization) {
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);
        if (doctors.isEmpty()) {
            throw new DoctorNotFoundException("No doctor(s) were found with that specialization: " + specialization);
        }
        return doctors.stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    @Override
    public List<DoctorDTO> getDoctorsByAvailibleFrom(LocalDateTime availibleFrom) {
        return doctorRepository.findDoctorsByAvailibleFrom(availibleFrom)
                .orElseThrow(() -> new DoctorNotFoundException("No doctor(s) were found available for that time: " + availibleFrom))
                .stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        return doctorRepository.findById(id)
                .map(doctorInDB -> {
                    doctorInDB.setFullName(doctorDTO.getFullName());
                    doctorInDB.setEmail(doctorDTO.getEmail());
                    doctorInDB.setAvailibleFrom(doctorDTO.getAvailableFrom());
                    doctorInDB.setAvailibleTo(doctorDTO.getAvailableTo());
                    return doctorMapper.toDTO(doctorRepository.save(doctorInDB));
                })
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + id));
    }

    @Override
    public void deleteDoctor(Long id) {
        if(!doctorRepository.existsById(id)) {
            throw new DoctorNotFoundException("Doctor not found with ID: " + id);
        }
        doctorRepository.deleteById(id);
    }
}
