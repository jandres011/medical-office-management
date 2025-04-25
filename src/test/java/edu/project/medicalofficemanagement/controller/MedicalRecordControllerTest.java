package edu.project.medicalofficemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.project.medicalofficemanagement.dto.MedicalRecordDTO;
import edu.project.medicalofficemanagement.service.MedicalRecordService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
@Import(MedicalRecordControllerTest.MockConfig.class)
class MedicalRecordControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MedicalRecordService medicalRecordService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public MedicalRecordService medicalRecordService() {
            return Mockito.mock(MedicalRecordService.class);
        }
    }

    @Test
    void createMedicalRecord_ShouldReturnCreated() throws Exception {
        MedicalRecordDTO dto = MedicalRecordDTO.builder()
                .id(1L)
                .appointmentId(1L)
                .patientId(1L)
                .diagnosis("Common cold")
                .notes("Rest for 3 days")
                .build();

        when(medicalRecordService.createMedicalRecord(any())).thenReturn(dto);

        mockMvc.perform(post("/api/medical-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diagnosis").value("Common cold"));
    }

    @Test
    void getAllMedicalRecords_ShouldReturnList() throws Exception {
        when(medicalRecordService.getAllMedicalRecords())
                .thenReturn(List.of(MedicalRecordDTO.builder().id(1L).diagnosis("Fever").build()));

        mockMvc.perform(get("/api/medical-records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].diagnosis").value("Fever"));
    }

    @Test
    void getMedicalRecordById_ShouldReturnRecord() throws Exception {
        MedicalRecordDTO dto = MedicalRecordDTO.builder().id(1L).diagnosis("Migraine").build();
        when(medicalRecordService.getMedicalRecordById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/medical-records/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getMedicalRecordsByPatientId_ShouldReturnList() throws Exception {
        when(medicalRecordService.getMedicalRecordsByPatientId(1L))
                .thenReturn(List.of(MedicalRecordDTO.builder().patientId(1L).build()));

        mockMvc.perform(get("/api/medical-records/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId").value(1L));
    }

    @Test
    void updateMedicalRecord_ShouldReturnUpdated() throws Exception {
        MedicalRecordDTO dto = MedicalRecordDTO.builder()
                .id(1L)
                .diagnosis("Updated diagnosis")
                .notes("Updated notes")
                .build();

        when(medicalRecordService.updateMedicalRecord(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/medical-records/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosis").value("Updated diagnosis"));
    }

    @Test
    void deleteMedicalRecord_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/medical-records/1"))
                .andExpect(status().isNoContent());

        verify(medicalRecordService).deleteMedicalRecord(1L);
    }
}