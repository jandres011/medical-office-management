package edu.project.medicalofficemanagement.controller;

import edu.project.medicalofficemanagement.dto.AppointmentDTO;
import edu.project.medicalofficemanagement.enums.status.Status;
import edu.project.medicalofficemanagement.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    private AppointmentDTO sampleDto;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start = LocalDateTime.of(2025, 5, 10, 9, 0);
        end = start.plusHours(1);
        sampleDto = AppointmentDTO.builder()
                .id(1L)
                .patientId(2L)
                .doctorId(3L)
                .consultRoomId(4L)
                .startTime(start)
                .endTime(end)
                .status(Status.SCHEDULED)
                .build();
    }

    @Test
    void getAllAppointments_returnsOkAndList() {
        when(appointmentService.getAllAppointments()).thenReturn(List.of(sampleDto));

        ResponseEntity<List<AppointmentDTO>> response = appointmentController.getAllAppointments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleDto, response.getBody().get(0));
        verify(appointmentService).getAllAppointments();
    }

    @Test
    void getAppointmentById_returnsOkAndDto() {
        when(appointmentService.getAppointmentById(1L)).thenReturn(sampleDto);

        ResponseEntity<AppointmentDTO> response = appointmentController.getAppointmentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleDto, response.getBody());
        verify(appointmentService).getAppointmentById(1L);
    }

    @Test
    void addAppointment_returnsCreatedAndDto() {
        AppointmentDTO req = AppointmentDTO.builder()
                .patientId(2L)
                .doctorId(3L)
                .consultRoomId(4L)
                .startTime(start)
                .endTime(end)
                .build();
        when(appointmentService.createAppointment(any(AppointmentDTO.class))).thenReturn(sampleDto);

        ResponseEntity<AppointmentDTO> response = appointmentController.addAppointment(req);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleDto, response.getBody());
        verify(appointmentService).createAppointment(req);
    }

    @Test
    void updateAppointment_returnsOkAndDto() {
        AppointmentDTO updateReq = AppointmentDTO.builder()
                .startTime(start)
                .endTime(end)
                .build();
        when(appointmentService.updateAppointment(eq(1L), any(AppointmentDTO.class))).thenReturn(sampleDto);

        ResponseEntity<AppointmentDTO> response = appointmentController.updateAppointment(1L, updateReq);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleDto, response.getBody());
        verify(appointmentService).updateAppointment(1L, updateReq);
    }

    @Test
    void deleteAppointment_returnsNoContent() {
        doNothing().when(appointmentService).deleteAppointment(1L);

        ResponseEntity<AppointmentDTO> response = appointmentController.deleteAppointment(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(appointmentService).deleteAppointment(1L);
    }
}
