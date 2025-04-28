package edu.project.medicalofficemanagement.service.impl;

import edu.project.medicalofficemanagement.dto.AppointmentDTO;
import edu.project.medicalofficemanagement.dto.mapper.AppointmentMapper;
import edu.project.medicalofficemanagement.exception.*;
import edu.project.medicalofficemanagement.model.Appointment;
import edu.project.medicalofficemanagement.model.ConsultRoom;
import edu.project.medicalofficemanagement.model.Doctor;
import edu.project.medicalofficemanagement.model.Patient;
import edu.project.medicalofficemanagement.repository.AppointmentRepository;
import edu.project.medicalofficemanagement.repository.ConsultRoomRepository;
import edu.project.medicalofficemanagement.repository.DoctorRepository;
import edu.project.medicalofficemanagement.repository.PatientRepository;
import edu.project.medicalofficemanagement.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ConsultRoomRepository consultRoomRepository;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        Patient patient = patientRepository.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID:" + appointmentDTO.getPatientId()));
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID:" + appointmentDTO.getDoctorId()));
        ConsultRoom consultRoom = consultRoomRepository.findById(appointmentDTO.getConsultRoomId())
                .orElseThrow(() -> new ConsultRoomNotFoundException("ConsultRoom not found with ID:" + appointmentDTO.getConsultRoomId()));

        List<Appointment> conflicts = appointmentRepository.findConflicts(doctor.getId(),
                consultRoom.getId(),
                appointmentDTO.getStartTime(),
                appointmentDTO.getEndTime());
        if(!conflicts.isEmpty()){
            throw new AppointmentAlreadyExistException("The appointment is already scheduled for the selected time.");
        }

        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setConsultRoom(consultRoom);

        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDTO)
                .toList();
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toDTO)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID:" + id));
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID:" + id));

        Patient patient = patientRepository.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID:" + appointmentDTO.getPatientId()));
        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID:" + appointmentDTO.getDoctorId()));
        ConsultRoom consultRoom = consultRoomRepository.findById(appointmentDTO.getConsultRoomId())
                .orElseThrow(() -> new ConsultRoomNotFoundException("ConsultRoom not found with ID:" + appointmentDTO.getConsultRoomId()));

        List<Appointment> conflicts = appointmentRepository.findConflicts(doctor.getId(),
                consultRoom.getId(),
                appointmentDTO.getStartTime(),
                appointmentDTO.getEndTime())
                .stream()
                .filter(b -> !b.getId().equals(id)) // Excluir la cita actual
                .toList();
        if(!conflicts.isEmpty()){
            throw new AppointmentAlreadyExistException("The appointment is already scheduled for the selected time.");
        }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setConsultRoom(consultRoom);

        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointment(Long id) {
        if(!appointmentRepository.existsById(id)){
            throw new AppointmentNotFoundException("Appointment not found with ID:" + id);
        }
        appointmentRepository.deleteById(id);
    }
}
