package edu.project.medicalofficemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.project.medicalofficemanagement.dto.PatientDTO;
import edu.project.medicalofficemanagement.service.PatientService;
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

@WebMvcTest(PatientController.class)
@Import(PatientControllerTest.MockConfig.class)
class PatientControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PatientService patientService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public PatientService patientService() {
            return Mockito.mock(PatientService.class);
        }
    }

    @Test
    void createPatient_ShouldReturnCreated() throws Exception {
        PatientDTO dto = PatientDTO.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .build();

        when(patientService.createPatient(any())).thenReturn(dto);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    @Test
    void getAllPatients_ShouldReturnList() throws Exception {
        when(patientService.getAllPatients())
                .thenReturn(List.of(PatientDTO.builder().id(1L).fullName("Jane Smith").build()));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Jane Smith"));
    }

    @Test
    void getPatientById_ShouldReturnPatient() throws Exception {
        PatientDTO dto = PatientDTO.builder().id(1L).email("test@example.com").build();
        when(patientService.getPatientById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getPatientByEmail_ShouldReturnPatient() throws Exception {
        PatientDTO dto = PatientDTO.builder().email("test@example.com").build();
        when(patientService.getPatientByEmail("test@example.com")).thenReturn(dto);

        mockMvc.perform(get("/api/patients/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void updatePatient_ShouldReturnUpdated() throws Exception {
        PatientDTO dto = PatientDTO.builder()
                .id(1L)
                .fullName("Updated Name")
                .build();

        when(patientService.updatePatient(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Name"));
    }

    @Test
    void deletePatient_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());

        verify(patientService).deletePatient(1L);
    }
}