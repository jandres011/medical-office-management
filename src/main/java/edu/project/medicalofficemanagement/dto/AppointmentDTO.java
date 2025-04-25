package edu.project.medicalofficemanagement.dto;

import edu.project.medicalofficemanagement.enums.status.Status;
import edu.project.medicalofficemanagement.validation.annotation.EnumValid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Long id;

    @NotNull(message = "El paciente es obligatorio")
    private Long patientId;

    @NotNull(message = "El doctor es obligatorio")
    private Long doctorId;

    @NotNull(message = "El consultorio es obligatorio")
    private Long consultRoomId;

    @Future(message = "La fecha debe ser a futuro")
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime startTime;

    @Future(message = "La fecha debe ser a futuro")
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime endTime;

    @EnumValid(value = Status.class, message = "Estado inválido")
    private Status status;
}