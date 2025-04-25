package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.ConsultRoomDTO;
import edu.project.medicalofficemanagement.dto.mapper.ConsultRoomMapper;
import edu.project.medicalofficemanagement.exception.ConsultRoomNotFoundException;
import edu.project.medicalofficemanagement.model.ConsultRoom;
import edu.project.medicalofficemanagement.repository.ConsultRoomRepository;
import edu.project.medicalofficemanagement.service.impl.ConsultRoomImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultRoomServiceTest {

    @Mock
    private ConsultRoomRepository consultRoomRepository;

    @Mock
    private ConsultRoomMapper consultRoomMapper;

    @InjectMocks
    private ConsultRoomImpl consultRoomService;

    private ConsultRoom consultRoom;
    private ConsultRoomDTO consultRoomDTO;

    @BeforeEach
    void setUp() {
        consultRoom = new ConsultRoom();
        consultRoom.setId(1L);
        consultRoom.setName("Room 101");
        consultRoom.setFloor("First Floor");

        consultRoomDTO = new ConsultRoomDTO();
        consultRoomDTO.setName("Room 101");
        consultRoomDTO.setFloor("First Floor");
    }

    @Test
    void createConsultRoom_ShouldSuccess() {
        when(consultRoomMapper.toEntity(consultRoomDTO)).thenReturn(consultRoom);
        when(consultRoomRepository.save(consultRoom)).thenReturn(consultRoom);
        when(consultRoomMapper.toDto(consultRoom)).thenReturn(consultRoomDTO);

        ConsultRoomDTO result = consultRoomService.createConsultRoom(consultRoomDTO);

        assertNotNull(result);
        assertEquals("Room 101", result.getName());
    }

    @Test
    void getConsultRoomById_ShouldReturnRoom() {
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.of(consultRoom));
        when(consultRoomMapper.toDto(consultRoom)).thenReturn(consultRoomDTO);

        ConsultRoomDTO result = consultRoomService.getConsultRoomById(1L);

        assertNotNull(result);
        assertEquals("First Floor", result.getFloor());
    }

    @Test
    void getConsultRoomById_ShouldThrowWhenNotFound() {
        when(consultRoomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConsultRoomNotFoundException.class, () -> {
            consultRoomService.getConsultRoomById(1L);
        });
    }

    @Test
    void getAllConsultRooms_ShouldReturnList() {
        when(consultRoomRepository.findAll()).thenReturn(List.of(consultRoom));
        when(consultRoomMapper.toDto(consultRoom)).thenReturn(consultRoomDTO);

        List<ConsultRoomDTO> result = consultRoomService.getAllConsultRooms();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}