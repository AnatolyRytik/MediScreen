package com.mediscreen.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PatientApiControllerTest {
    @Mock
    private PatientService patientService;

    private PatientApiController patientApiController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientApiController = new PatientApiController(patientService);
        objectMapper = new ObjectMapper();
    }

    @Test
    void createPatient_ValidPatientDto_ReturnsCreatedResponse() throws Exception {
        // Arrange
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John");
        patientDto.setLastName("Doe");
        patientDto.setBirthDate(LocalDate.of(1990, 1, 1));
        patientDto.setGender("Male");

        Patient createdPatient = new Patient();
        createdPatient.setId(1L);
        createdPatient.setFirstName("John");
        createdPatient.setLastName("Doe");
        createdPatient.setBirthDate(LocalDate.of(1990, 1, 1));
        createdPatient.setGender("Male");

        when(patientService.createPatient(any(PatientDto.class))).thenReturn(createdPatient);

        // Act
        ResponseEntity<Patient> responseEntity = patientApiController.createPatient(patientDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(createdPatient, responseEntity.getBody());
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

        when(patientService.getPatient(eq(patientId))).thenReturn(patient);

        // Act
        ResponseEntity<?> responseEntity = patientApiController.getPatient(patientId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(patient, responseEntity.getBody());
    }

    @Test
    void getPatient_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        // Arrange
        Long patientId = 1L;

        when(patientService.getPatient(eq(patientId))).thenThrow(new NotFoundException("Patient not found"));

        // Act
        ResponseEntity<?> responseEntity = patientApiController.getPatient(patientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
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

        // Act
        ResponseEntity<List<Patient>> responseEntity = patientApiController.getAllPatients();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(patients, responseEntity.getBody());
    }

    @Test
    void updatePatient_ExistingIdAndValidPatientDto_ReturnsUpdatedPatient() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John");
        patientDto.setLastName("Doe");
        patientDto.setBirthDate(LocalDate.of(1990, 1, 1));
        patientDto.setGender("Male");

        Patient existingPatient = new Patient();
        existingPatient.setId(patientId);
        existingPatient.setFirstName("John");
        existingPatient.setLastName("Doe");
        existingPatient.setBirthDate(LocalDate.of(1990, 1, 1));
        existingPatient.setGender("Male");

        Patient updatedPatient = new Patient();
        updatedPatient.setId(patientId);
        updatedPatient.setFirstName("John");
        updatedPatient.setLastName("Doe");
        updatedPatient.setBirthDate(LocalDate.of(1990, 1, 1));
        updatedPatient.setGender("Male");

        when(patientService.updatePatient(eq(patientId), any(PatientDto.class))).thenReturn(updatedPatient);

        // Act
        ResponseEntity<?> responseEntity = patientApiController.updatePatient(patientId, patientDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedPatient, responseEntity.getBody());
    }

    @Test
    void updatePatient_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John");
        patientDto.setLastName("Doe");
        patientDto.setBirthDate(LocalDate.of(1990, 1, 1));
        patientDto.setGender("Male");

        when(patientService.updatePatient(eq(patientId), any(PatientDto.class))).thenThrow(new NotFoundException("Patient not found"));

        // Act
        ResponseEntity<?> responseEntity = patientApiController.updatePatient(patientId, patientDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deletePatient_ExistingId_ReturnsNoContentResponse() throws Exception {
        // Arrange
        Long patientId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = patientApiController.deletePatient(patientId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(patientService, times(1)).deletePatient(eq(patientId));
    }

    @Test
    void deletePatient_NonExistingId_ReturnsNotFoundResponse() throws Exception {
        // Arrange
        Long patientId = 1L;

        doThrow(new NotFoundException("Patient not found")).when(patientService).deletePatient(eq(patientId));

        // Act
        ResponseEntity<Void> responseEntity = patientApiController.deletePatient(patientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(patientService, times(1)).deletePatient(eq(patientId));
    }
}
