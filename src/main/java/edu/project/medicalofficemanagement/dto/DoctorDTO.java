package edu.project.medicalofficemanagement.dto;

import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DoctorDTO {

    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private Specialization specialization;

    @Future
    private LocalDate availibleFrom;

    @Future
    private LocalDate availibleTo;

}
