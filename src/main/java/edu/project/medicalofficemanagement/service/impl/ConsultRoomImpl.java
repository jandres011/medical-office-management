package edu.project.medicalofficemanagement.service.impl;

import edu.project.medicalofficemanagement.dto.ConsultRoomDTO;
import edu.project.medicalofficemanagement.dto.mapper.ConsultRoomMapper;
import edu.project.medicalofficemanagement.exception.ConsultRoomNotFoundException;
import edu.project.medicalofficemanagement.model.ConsultRoom;
import edu.project.medicalofficemanagement.repository.ConsultRoomRepository;
import edu.project.medicalofficemanagement.service.ConsultRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultRoomImpl implements ConsultRoomService {
    private final ConsultRoomMapper consultRoomMapper;
    private final ConsultRoomRepository consultRoomRepository;

    @Override
    public ConsultRoomDTO createConsultRoom(ConsultRoomDTO consultRoomDTO) {
        ConsultRoom consultRoom = consultRoomMapper.toEntity(consultRoomDTO);
        return consultRoomMapper.toDto(consultRoomRepository.save(consultRoom));
    }

    @Override
    public List<ConsultRoomDTO> getAllConsultRooms() {
        return consultRoomRepository.findAll()
                .stream()
                .map(consultRoomMapper::toDto)
                .toList();
    }

    @Override
    public ConsultRoomDTO getConsultRoomById(Long id) {
        return consultRoomRepository.findById(id)
                .map(consultRoomMapper::toDto)
                .orElseThrow(() -> new ConsultRoomNotFoundException("Consult Room not found with ID: " + id));
    }

    @Override
    public ConsultRoomDTO updateConsultRoom(Long id, ConsultRoomDTO consultRoomDTO) {
        return consultRoomRepository.findById(id)
                .map(consultRoomInDB -> {
                    consultRoomInDB.setName(consultRoomDTO.getName());
                    consultRoomInDB.setFloor(consultRoomDTO.getFloor());
                    consultRoomInDB.setDescription(consultRoomDTO.getDescription());
                    return consultRoomMapper.toDto(consultRoomRepository.save(consultRoomInDB));
                })
                .orElseThrow(() -> new ConsultRoomNotFoundException("Consult Room not found with ID: " + id));
    }

    @Override
    public void deleteConsultRoom(Long id) {
        if(!consultRoomRepository.existsById(id)) {
            throw new ConsultRoomNotFoundException("Consult Room not found with ID: " + id);
        }
        consultRoomRepository.deleteById(id);
    }
}
