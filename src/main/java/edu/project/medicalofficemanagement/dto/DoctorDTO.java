package edu.project.medicalofficemanagement.dto;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.enums.status.Status;
import edu.project.medicalofficemanagement.validation.annotation.EnumValid;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DoctorDTO {

    private Long id;

    @NotBlank(message = "El nombre del médico es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String fullName;

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Enumerated(EnumType.STRING)
    @EnumValid(value = Status.class, message = "Especialización inválida")
    private Specialization specialization;

    @Future(message = "La fecha de disponibilidad debe ser a futuro")
    @NotNull(message = "La fecha de inicio de disponibilidad es obligatoria")
    private LocalDateTime availableFrom;

    @Future(message = "La fecha de disponibilidad debe ser a futuro")
    @NotNull(message = "La fecha de fin de disponibilidad es obligatoria")
    private LocalDateTime availableTo;
}