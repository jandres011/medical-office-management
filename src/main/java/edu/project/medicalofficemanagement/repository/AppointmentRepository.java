package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT a FROM Appointment a " +
            "WHERE (a.doctor.id = :idDoctor OR a.consultRoom.id = :idConsultRoom) " +
            "AND (:startTime < a.endTime AND :endTime > a.startTime)")
    List<Appointment> findConflicts(@Param("idDoctor") Long idDoctor,
                                    @Param("idConsultRoom") Long idConsultRoom,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);
}
