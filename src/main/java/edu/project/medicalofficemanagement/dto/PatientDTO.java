package edu.project.medicalofficemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {

    private Long id;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String fullName;

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El número de teléfono debe contener solo dígitos y tener entre 10 y 15 caracteres")
    private String phoneNumber;
}