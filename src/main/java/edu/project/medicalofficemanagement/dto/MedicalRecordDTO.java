package edu.project.medicalofficemanagement.dto;


import edu.project.medicalofficemanagement.model.Appointment;
import edu.project.medicalofficemanagement.model.Patient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordDTO {

    @NotNull
    private Appointment appointment;

    @NotNull
    private Patient patient;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String notes;

    private LocalDateTime createdAt;
}
