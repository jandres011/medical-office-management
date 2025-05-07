package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.ConsultRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultRoomRepository extends JpaRepository<ConsultRoom, Long> {
    Optional<ConsultRoom> findByName(String name);

    List<ConsultRoom> findByDescriptionContaining(String description);
}
