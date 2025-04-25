package edu.project.medicalofficemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultRoomDTO {

    private Long id;

    @NotBlank(message = "El nombre del consultorio es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "El piso es obligatorio")
    @Size(min = 1, max = 10, message = "El piso debe tener entre 1 y 10 caracteres")
    private String floor;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String description;
}