package edu.project.medicalofficemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordDTO {
    private Long id;

    @NotNull(message = "La cita es obligatoria")
    private Long appointmentId;

    @NotNull(message = "Elpaciente es obligatorio")
    private Long patientId;

    @NotBlank(message = "El diagnóstico no puede estar vacío")
    private String diagnosis;

    @NotBlank(message = "Las notas no pueden estar vacías")
    private String notes;

    private LocalDateTime createdAt;
}