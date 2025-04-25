package edu.project.medicalofficemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.project.medicalofficemanagement.dto.DoctorDTO;
import edu.project.medicalofficemanagement.enums.specialization.Specialization;
import edu.project.medicalofficemanagement.service.DoctorService;
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

@WebMvcTest(DoctorController.class)
@Import(DoctorControllerTest.MockConfig.class)
class DoctorControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private DoctorService doctorService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public DoctorService doctorService() {
            return Mockito.mock(DoctorService.class);
        }
    }

    @Test
    void createDoctor_ShouldReturnCreated() throws Exception {
        DoctorDTO dto = DoctorDTO.builder()
                .id(1L)
                .fullName("Dr. Smith")
                .specialization(Specialization.CARDIOLOGY)
                .build();

        when(doctorService.createDoctor(any())).thenReturn(dto);

        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.specialization").value("CARDIOLOGY"));
    }

    @Test
    void getAllDoctors_ShouldReturnList() throws Exception {
        when(doctorService.getAllDoctors())
                .thenReturn(List.of(DoctorDTO.builder().id(1L).fullName("Dr. Johnson").build()));

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Dr. Johnson"));
    }

    @Test
    void getDoctorById_ShouldReturnDoctor() throws Exception {
        DoctorDTO dto = DoctorDTO.builder().id(1L).fullName("Dr. Williams").build();
        when(doctorService.getDoctorById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Dr. Williams"));
    }

    @Test
    void getDoctorsBySpecialization_ShouldReturnList() throws Exception {
        when(doctorService.getDoctorsBySpecialization(Specialization.NEUROLOGY))
                .thenReturn(List.of(DoctorDTO.builder().specialization(Specialization.NEUROLOGY).build()));

        mockMvc.perform(get("/api/doctors/specialization/NEUROLOGY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specialization").value("NEUROLOGY"));
    }

    @Test
    void updateDoctor_ShouldReturnUpdated() throws Exception {
        DoctorDTO dto = DoctorDTO.builder()
                .id(1L)
                .fullName("Dr. Updated")
                .build();

        when(doctorService.updateDoctor(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Dr. Updated"));
    }

    @Test
    void deleteDoctor_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/doctors/1"))
                .andExpect(status().isNoContent());

        verify(doctorService).deleteDoctor(1L);
    }
}