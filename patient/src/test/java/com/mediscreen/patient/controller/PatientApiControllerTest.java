package com.mediscreen.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.util.exception.NotFoundException;
import com.mediscreen.patient.util.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatientApiControllerTest {
    private final PatientMapper patientMapper = new PatientMapper();
    @Mock
    private PatientService patientService;
    @InjectMocks
    private PatientApiController patientApiController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientApiController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createPatient_ValidPatientDto_ReturnsCreatedResponse() throws Exception {
        // Arrange
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John");
        patientDto.setLastName("Doe");
        patientDto.setGender("Male");

        Patient createdPatient = patientMapper.toEntity(patientDto);
        createdPatient.setId(1L);
        when(patientService.createPatient(patientDto)).thenReturn(createdPatient);

        // Act & Assert
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void getPatient_ExistingId_ReturnsPatient() throws Exception {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setBirthDate(LocalDate.of(1990, 1, 1));
        patient.setGender("Male");

        when(patientService.getPatient(patientId)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(get("/api/patients/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(patientId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void getPatient_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        // Arrange
        Long patientId = 1L;

        when(patientService.getPatient(patientId)).thenThrow(new NotFoundException("Patient not found"));

        // Act & Assert
        mockMvc.perform(get("/api/patients/{id}", patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllPatients_ReturnsListOfPatients() throws Exception {
        // Arrange
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setFirstName("John");
        patient1.setLastName("Doe");
        patient1.setBirthDate(LocalDate.of(1990, 1, 1));
        patient1.setGender("Male");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setFirstName("Jane");
        patient2.setLastName("Smith");
        patient2.setBirthDate(LocalDate.of(1995, 5, 5));
        patient2.setGender("Female");

        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientService.getAllPatients()).thenReturn(patients);

        // Act & Assert
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));
    }

    @Test
    void updatePatient_ExistingIdAndValidPatientDto_ReturnsUpdatedPatient() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("Updated");
        patientDto.setLastName("Patient");
        patientDto.setGender("Male");

        Patient existingPatient = new Patient();
        existingPatient.setId(patientId);
        existingPatient.setFirstName("John");
        existingPatient.setLastName("Doe");
        existingPatient.setGender("Male");
        Patient updatedPatient = patientMapper.toEntity(patientDto);
        updatedPatient.setId(patientId);
        when(patientService.updatePatient(patientId, patientDto)).thenReturn(updatedPatient);

        // Act & Assert
        mockMvc.perform(put("/api/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Patient"));
    }

    @Test
    void updatePatient_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("Updated");
        patientDto.setLastName("Patient");
        patientDto.setGender("Male");
        when(patientService.updatePatient(patientId, patientDto)).thenThrow(new NotFoundException("Patient not found"));

        // Act & Assert
        mockMvc.perform(put("/api/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePatient_ExistingId_ReturnsNoContentResponse() throws Exception {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(patientService.getPatient(patientId)).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(delete("/api/patients/{id}", patientId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePatient_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        // Arrange
        Long patientId = 1L;

        doThrow(new NotFoundException("Patient not found")).when(patientService).deletePatient(eq(patientId));

        // Act & Assert
        mockMvc.perform(delete("/api/patients/{id}", patientId))
                .andExpect(status().isNotFound());
    }
}
