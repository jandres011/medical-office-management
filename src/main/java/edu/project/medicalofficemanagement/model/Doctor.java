package edu.project.medicalofficemanagement.model;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.enums.status.Status;
import edu.project.medicalofficemanagement.validation.annotation.EnumValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del médico es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String fullName;

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Enumerated(EnumType.STRING)
    @EnumValid(value = Specialization.class, message = "Especialización inválida")
    private Specialization specialization;

    @Future(message = "La fecha de disponibilidad debe ser a futuro")
    @NotNull(message = "La fecha de inicio de disponibilidad es obligatoria")
    private LocalDateTime availibleFrom;

    @Future(message = "La fecha de disponibilidad debe ser a futuro")
    @NotNull(message = "La fecha de fin de disponibilidad es obligatoria")
    private LocalDateTime availibleTo;
}
