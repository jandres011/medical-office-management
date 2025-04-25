package edu.project.medicalofficemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    @NotNull(message = "La cita es obligatoria")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "El paciente es obligatorio")
    private Patient patient;

    @NotBlank(message = "El diagnóstico no puede estar vacío")
    private String diagnosis;

    @NotBlank(message = "Las notas no pueden estar vacías")
    private String notes;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
