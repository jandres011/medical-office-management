package edu.project.medicalofficemanagement.DTO;

import edu.project.medicalofficemanagement.model.ConsultRoom;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Long id;

    @NotNull
    private String patientName;

    @NotNull
    private String doctorName;

    @NotNull
    private ConsultRoom consultRoom;

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;


}
