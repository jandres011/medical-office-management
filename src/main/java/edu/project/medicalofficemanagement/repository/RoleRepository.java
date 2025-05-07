package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
