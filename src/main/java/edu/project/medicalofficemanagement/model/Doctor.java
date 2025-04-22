package edu.project.medicalofficemanagement.model;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.enums.status.Status;
import edu.project.medicalofficemanagement.validation.annotation.EnumValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Este campo no puede estar vacío")
    private String fullName;

    @NotBlank(message = "Este campo no puede estar vacío")
    private String email;

    @Enumerated(EnumType.STRING)
    @EnumValid(value = Status.class, message = "Especialización inválida")
    private Specialization specialization;

    @Future(message = "La fecha debe ser a futuro") @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime availibleFrom;

    @Future(message = "La fecha debe ser a futuro") @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime availibleTo;

}
