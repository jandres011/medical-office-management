package edu.project.medicalofficemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.project.medicalofficemanagement.dto.ConsultRoomDTO;
import edu.project.medicalofficemanagement.service.ConsultRoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultRoomController.class)
@Import(ConsultRoomControllerTest.MockConfig.class)
class ConsultRoomControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ConsultRoomService consultRoomService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ConsultRoomService consultRoomService() {
            return Mockito.mock(ConsultRoomService.class);
        }
    }

    @Test
    void createConsultRoom_ShouldReturnCreated() throws Exception {
        ConsultRoomDTO dto = ConsultRoomDTO.builder()
                .id(1L)
                .name("Room 101")
                .floor("First Floor")
                .build();

        when(consultRoomService.createConsultRoom(any())).thenReturn(dto);

        mockMvc.perform(post("/api/consult-rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Room 101"));
    }

    @Test
    void getAllConsultRooms_ShouldReturnList() throws Exception {
        when(consultRoomService.getAllConsultRooms())
                .thenReturn(List.of(ConsultRoomDTO.builder().id(1L).name("Room 202").build()));

        mockMvc.perform(get("/api/consult-rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Room 202"));
    }

    @Test
    void getConsultRoomById_ShouldReturnRoom() throws Exception {
        ConsultRoomDTO dto = ConsultRoomDTO.builder().id(1L).floor("Second Floor").build();
        when(consultRoomService.getConsultRoomById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/consult-rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.floor").value("Second Floor"));
    }

    @Test
    void updateConsultRoom_ShouldReturnUpdated() throws Exception {
        ConsultRoomDTO dto = ConsultRoomDTO.builder()
                .id(1L)
                .name("Updated Room")
                .build();

        when(consultRoomService.updateConsultRoom(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/consult-rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Room"));
    }

    @Test
    void deleteConsultRoom_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/consult-rooms/1"))
                .andExpect(status().isNoContent());

        verify(consultRoomService).deleteConsultRoom(1L);
    }
}