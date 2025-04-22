package edu.project.medicalofficemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Este campo no puede estar vacío")
    private String fullName;

    @NotBlank(message = "Este campo no puede estar vacío") @Email
    private String email;

    @NotBlank(message = "Este campo no puede estar vacío")
    private String phoneNumber;

}
