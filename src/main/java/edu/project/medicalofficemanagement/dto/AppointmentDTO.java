package edu.project.medicalofficemanagement.dto;

import edu.project.medicalofficemanagement.model.ConsultRoom;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;

    @NotBlank
    private String patientName;

    @NotBlank
    private String doctorName;

    @NotNull
    private ConsultRoom consultRoom;

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;


}
