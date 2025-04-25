package edu.project.medicalofficemanagement.controller;

import edu.project.medicalofficemanagement.dto.ConsultRoomDTO;
import edu.project.medicalofficemanagement.service.ConsultRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consultRooms")
@RequiredArgsConstructor
public class ConsultRoomController {
    private final ConsultRoomService consultRoomService;

    @PostMapping
    public ResponseEntity<ConsultRoomDTO> createConsultRoom(@RequestBody @Valid ConsultRoomDTO consultRoomDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultRoomService.createConsultRoom(consultRoomDTO));
    }

    @GetMapping
    public ResponseEntity<List<ConsultRoomDTO>> getAllConsultRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(consultRoomService.getAllConsultRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultRoomDTO> getConsultRoomById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultRoomService.getConsultRoomById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultRoomDTO> updateConsultRoom(@PathVariable Long id, @RequestBody @Valid ConsultRoomDTO consultRoomDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(consultRoomService.updateConsultRoom(id, consultRoomDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultRoom(@PathVariable Long id) {
        consultRoomService.deleteConsultRoom(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
