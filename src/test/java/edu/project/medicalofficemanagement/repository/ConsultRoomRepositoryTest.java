package edu.project.medicalofficemanagement.repository;

import edu.project.medicalofficemanagement.model.ConsultRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class ConsultRoomRepositoryTest {

    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    @Test
    void shouldSaveConsultRoom() {
        ConsultRoom room = ConsultRoom.builder()
                .name("Room 101")
                .floor("Floor 1")
                .description("Cardiology Room")
                .build();

        ConsultRoom saved = consultRoomRepository.save(room);

        assertNotNull(saved.getId());
        assertEquals("Room 101", saved.getName());
    }

    @Test
    void shouldFindConsultRoomByName() {
        consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 202")
                .floor("Floor 2")
                .description("Neurology Room")
                .build());

        Optional<ConsultRoom> found = consultRoomRepository.findByName(("Room 202"));

        assertTrue(found.isPresent());
        assertEquals("Floor 2", found.get().getFloor());
    }

    @Test
    void shouldReturnEmptyWhenConsultRoomNotFound() {
        Optional<ConsultRoom> found = consultRoomRepository.findByName("Non-existent Room");
        assertFalse(found.isPresent());
    }

    @Test
    void shouldFindAvailableConsultRooms() {
        consultRoomRepository.save(ConsultRoom.builder()
                .name("Room 303")
                .floor("Floor 3")
                .description("Available Room")
                .build());

        List<ConsultRoom> availableRooms = consultRoomRepository.findByDescriptionContaining(("Available"));

        assertFalse(availableRooms.isEmpty());
        assertEquals(1, availableRooms.size());
    }
}
