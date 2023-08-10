package com.mediscreen.notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.notes.dto.PatientNoteDto;
import com.mediscreen.notes.model.PatientNote;
import com.mediscreen.notes.service.PatientNoteService;
import com.mediscreen.notes.util.exception.NotFoundException;
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

public class PatientNoteApiControllerTests {
    @Mock
    private PatientNoteService patientNoteService;

    @InjectMocks
    private PatientNoteApiController patientNoteApiController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientNoteApiController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllPatientNotes() throws Exception {
        // Arrange
        PatientNote patientNote1 = new PatientNote();
        patientNote1.setId("1");
        PatientNote patientNote2 = new PatientNote();
        patientNote2.setId("2");
        List<PatientNote> patientNotes = Arrays.asList(patientNote1, patientNote2);
        when(patientNoteService.getAllPatientNotes()).thenReturn(patientNotes);

        // Act & Assert
        mockMvc.perform(get("/api/patient-notes/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreatePatientNote() throws Exception {
        // Arrange
        PatientNoteDto patientNoteDto = new PatientNoteDto(1l, "Sample note");
        PatientNote createdPatientNote = new PatientNote(patientNoteDto);
        createdPatientNote.setId("333");
        when(patientNoteService.createPatientNote(patientNoteDto)).thenReturn(createdPatientNote);

        // Act & Assert
        mockMvc.perform(post("/api/patient-notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientNoteDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("333"))
                .andExpect(jsonPath("$.patientId").value(1l));
    }

    @Test
    public void testGetPatientNote_ExistingNote() throws Exception {
        // Arrange
        String id = "1";
        PatientNote patientNote = new PatientNote();
        patientNote.setId(id);
        when(patientNoteService.getPatientNoteById(id)).thenReturn(patientNote);

        // Act & Assert
        mockMvc.perform(get("/api/patient-notes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void testGetPatientNote_NonExistingNote() throws Exception {
        // Arrange
        String id = "1";
        when(patientNoteService.getPatientNoteById(id)).thenThrow(new NotFoundException("Not found"));

        // Act & Assert
        mockMvc.perform(get("/api/patient-notes/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllPatientNotesByPatientId() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientNote patientNote1 = new PatientNote();
        patientNote1.setId("1");
        PatientNote patientNote2 = new PatientNote();
        patientNote2.setId("2");
        List<PatientNote> patientNotes = Arrays.asList(patientNote1, patientNote2);
        when(patientNoteService.getAllPatientNotesByPatientId(patientId)).thenReturn(patientNotes);

        // Act & Assert
        mockMvc.perform(get("/api/patient-notes/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testUpdatePatientNote_ExistingNote() throws Exception {
        // Arrange
        String id = "1";
        PatientNoteDto patientNoteDto = new PatientNoteDto();
        patientNoteDto.setPatientId(1l);
        patientNoteDto.setNote("Hello");
        PatientNote existingPatientNote = new PatientNote();
        existingPatientNote.setId(id);
        existingPatientNote.setCreationDate(LocalDate.of(2023, 6, 16)); // Set a valid creationDate
        PatientNote updatedPatientNote = new PatientNote();
        updatedPatientNote.setId(id);
        updatedPatientNote.setCreationDate(LocalDate.now()); // Set the current date as creationDate
        when(patientNoteService.updatePatientNote(id, patientNoteDto)).thenReturn(updatedPatientNote);

        // Act & Assert
        mockMvc.perform(put("/api/patient-notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientNoteDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void testUpdatePatientNote_NonExistingNote() throws Exception {
        // Arrange
        String id = "1";
        PatientNoteDto patientNoteDto = new PatientNoteDto();
        patientNoteDto.setPatientId(1l);
        patientNoteDto.setNote("Hello");
        when(patientNoteService.updatePatientNote(id, patientNoteDto)).thenThrow(new NotFoundException("Not found"));

        // Act & Assert
        mockMvc.perform(put("/api/patient-notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientNoteDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePatientNote_ExistingNote() throws Exception {
        // Arrange
        String id = "1";
        PatientNote patientNote = new PatientNote();
        patientNote.setId(id);
        when(patientNoteService.getPatientNoteById(id)).thenReturn(patientNote);

        // Act & Assert
        mockMvc.perform(delete("/api/patient-notes/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePatientNote_NonExistingNote() throws Exception {
        // Arrange
        String id = "111";
        doThrow(new NotFoundException("Patient not found")).when(patientNoteService).deletePatientNoteById(eq(id));

        // Act & Assert
        mockMvc.perform(delete("/api/patient-notes/{id}", id))
                .andExpect(status().isNotFound());
    }
}
