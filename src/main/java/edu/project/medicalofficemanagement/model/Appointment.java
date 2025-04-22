package edu.project.medicalofficemanagement.model;

import edu.project.medicalofficemanagement.enums.status.Status;
import edu.project.medicalofficemanagement.validation.annotation.EnumValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "El paciente es obligatorio")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "El doctor es obligatorio")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "consultRoom_id", nullable = false)
    @NotNull(message = "El consultorio es obligatorio")
    private ConsultRoom consultRoom;

    @Future(message = "La fecha debe ser a futuro")
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime startTime;

    @Future(message = "La fecha debe ser a futuro")
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @EnumValid(value = Status.class, message = "Estado inválido")
    private Status status;
}
