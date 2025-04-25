package edu.project.medicalofficemanagement.service;

import edu.project.medicalofficemanagement.dto.ConsultRoomDTO;

import java.util.List;

public interface ConsultRoomService {
    ConsultRoomDTO createConsultRoom(ConsultRoomDTO consultRoomDTO);
    List<ConsultRoomDTO> getAllConsultRooms();
    ConsultRoomDTO getConsultRoomById(Long id);
    ConsultRoomDTO updateConsultRoom(Long id, ConsultRoomDTO consultRoomDTO);
    void deleteConsultRoom(Long id);
}
