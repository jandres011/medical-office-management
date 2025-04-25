package edu.project.medicalofficemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.project.medicalofficemanagement.dto.AppointmentDTO;
import edu.project.medicalofficemanagement.enums.status.Status;
import edu.project.medicalofficemanagement.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
@Import(AppointmentControllerTest.MockConfig.class)
class AppointmentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AppointmentService appointmentService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public AppointmentService appointmentService() {
            return Mockito.mock(AppointmentService.class);
        }
    }

    @Test
    void addAppointment_ShouldReturnCreated() throws Exception {
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(1L)
                .patientId(1L)
                .doctorId(1L)
                .consultRoomId(1L)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .status(Status.SCHEDULED)
                .build();

        when(appointmentService.createAppointment(any())).thenReturn(dto);

        mockMvc.perform(post("/api/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
    }

    @Test
    void getAllAppointments_ShouldReturnList() throws Exception {
        List<AppointmentDTO> list = new ArrayList<>();
        list.add(AppointmentDTO.builder().id(1L).patientId(1L).build());
        list.add(AppointmentDTO.builder().id(2L).patientId(2L).build());

        when(appointmentService.getAllAppointments()).thenReturn(list);

        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].patientId").value(1L));
    }

    @Test
    void getAppointmentById_ShouldReturnAppointment() throws Exception {
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(1L)
                .patientId(1L)
                .doctorId(1L)
                .consultRoomId(1L)
                .build();

        when(appointmentService.getAppointmentById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.consultRoomId").value(1L));
    }

    @Test
    void updateAppointment_ShouldReturnUpdated() throws Exception {
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(1L)
                .patientId(1L)
                .doctorId(2L)
                .consultRoomId(2L)
                .startTime(LocalDateTime.now().plusDays(2))
                .endTime(LocalDateTime.now().plusDays(2).plusHours(1))
                .status(Status.SCHEDULED)
                .build();

        when(appointmentService.updateAppointment(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/v1/appointments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorId").value(2L))
                .andExpect(jsonPath("$.status").value("RESCHEDULED"));
    }

    @Test
    void deleteAppointment_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/appointments/1"))
                .andExpect(status().isNoContent());

        verify(appointmentService).deleteAppointment(1L);
    }
}
