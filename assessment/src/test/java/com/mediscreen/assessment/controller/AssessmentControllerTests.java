package com.mediscreen.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.assessment.constants.RiskLevel;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.exception.NotFoundException;
import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.service.AssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AssessmentControllerTests {

    @Mock
    private AssessmentService assessmentService;

    @InjectMocks
    private AssessmentController assessmentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assessmentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetPatientRiskLevel_Success() throws Exception {
        // Arrange
        Long id = 1L;
        PatientDto patientDto = new PatientDto();

        Report report = new Report(patientDto, 25, RiskLevel.BORDERLINE);
        when(assessmentService.getPatientRiskLevel(id)).thenReturn(report);

        // Act & Assert
        mockMvc.perform(get("/api/assessments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.riskLevel").value("BORDERLINE"));
    }

    @Test
    public void testGetPatientRiskLevel_PatientNotFound() throws Exception {
        // Arrange
        Long id = 1L;
        when(assessmentService.getPatientRiskLevel(id)).thenThrow(new NotFoundException("Patient not found with ID: " + id));

        // Act & Assert
        mockMvc.perform(get("/api/assessments/{id}", id))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetPatientRiskLevel_BadRequest() throws Exception {
        // Arrange
        Long id = 1L;
        when(assessmentService.getPatientRiskLevel(id)).thenThrow(new IllegalArgumentException("Invalid argument"));

        // Act & Assert
        mockMvc.perform(get("/api/assessments/{id}", id))
                .andExpect(status().isBadRequest());
    }
}
