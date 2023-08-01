package com.mediscreen.notes.service;

import com.mediscreen.notes.dto.PatientNoteDto;
import com.mediscreen.notes.model.PatientNote;
import com.mediscreen.notes.proxy.PatientProxy;
import com.mediscreen.notes.repository.PatientNoteRepository;
import com.mediscreen.notes.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
public class PatientNoteServiceTests {
    @Mock
    private PatientNoteRepository patientNoteRepository;

    @Mock
    private PatientProxy patientProxy;

    @InjectMocks
    private PatientNoteServiceImpl patientNoteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePatientNote() {
        // Arrange
        PatientNoteDto patientNoteDto = new PatientNoteDto();
        patientNoteDto.setPatientId(1l);
        patientNoteDto.setNote("Hello");
        PatientNote patientNote = new PatientNote(patientNoteDto);
        patientNote.setCreationDate(LocalDate.now());
        when(patientNoteRepository.save(patientNote)).thenReturn(patientNote);

        // Act
        PatientNote createdPatientNote = patientNoteService.createPatientNote(patientNoteDto);

        // Assert
        Assertions.assertEquals(patientNote, createdPatientNote);
        verify(patientNoteRepository, times(1)).save(patientNote);
    }

    @Test
    public void testGetPatientNoteById_ExistingNote() {
        // Arrange
        String id = "1";
        PatientNote patientNote = new PatientNote();
        when(patientNoteRepository.findById(id)).thenReturn(Optional.of(patientNote));

        // Act
        PatientNote retrievedPatientNote = patientNoteService.getPatientNoteById(id);

        // Assert
        Assertions.assertEquals(patientNote, retrievedPatientNote);
        verify(patientNoteRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPatientNoteById_NonExistingNote() {
        // Arrange
        String id = "1";
        when(patientNoteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            patientNoteService.getPatientNoteById(id);
        });
        verify(patientNoteRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllPatientNotes() {
        // Arrange
        List<PatientNote> patientNotes = Arrays.asList(new PatientNote(), new PatientNote());
        when(patientNoteRepository.findAll()).thenReturn(patientNotes);

        // Act
        List<PatientNote> retrievedPatientNotes = patientNoteService.getAllPatientNotes();

        // Assert
        Assertions.assertEquals(patientNotes.size(), retrievedPatientNotes.size());
        verify(patientNoteRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllPatientNotesByPatientId() {
        // Arrange
        Long patientId = 1L;
        List<PatientNote> patientNotes = Arrays.asList(new PatientNote(), new PatientNote());
        when(patientNoteRepository.getAllByPatientId(patientId)).thenReturn(patientNotes);

        // Act
        List<PatientNote> retrievedPatientNotes = patientNoteService.getAllPatientNotesByPatientId(patientId);

        // Assert
        Assertions.assertEquals(patientNotes.size(), retrievedPatientNotes.size());
        verify(patientNoteRepository, times(1)).getAllByPatientId(patientId);
    }

    @Test
    public void testUpdatePatientNote_ExistingNote() {
        // Arrange
        String id = "ABC123";
        PatientNoteDto patientNoteDto = new PatientNoteDto(12345l, "existing");
        PatientNote existingPatientNote = new PatientNote(id, 12345l, LocalDate.now(), "existing");
        PatientNote updatedPatientNote = new PatientNote(patientNoteDto);
        updatedPatientNote.setId(id);
        updatedPatientNote.setCreationDate(existingPatientNote.getCreationDate());
        when(patientNoteRepository.findById(id)).thenReturn(Optional.of(existingPatientNote));
        when(patientNoteRepository.save(updatedPatientNote)).thenReturn(updatedPatientNote);

        // Act
        PatientNote result = patientNoteService.updatePatientNote(id, patientNoteDto);

        // Assert
        Assertions.assertEquals(updatedPatientNote, result);
        verify(patientNoteRepository, times(1)).findById(id);
        verify(patientNoteRepository, times(1)).save(updatedPatientNote);
    }

    @Test
    public void testUpdatePatientNote_NonExistingNote() {
        // Arrange
        String id = "1";
        PatientNoteDto patientNoteDto = new PatientNoteDto();
        when(patientNoteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            patientNoteService.updatePatientNote(id, patientNoteDto);
        });
        verify(patientNoteRepository, times(1)).findById(id);
    }

    @Test
    public void testDeletePatientNote_ExistingNote() {
        // Arrange
        String id = "1";
        PatientNote patientNote = new PatientNote();
        when(patientNoteRepository.findById(id)).thenReturn(Optional.of(patientNote));

        // Act
        patientNoteService.deletePatientNoteById(id);

        // Assert
        verify(patientNoteRepository, times(1)).findById(id);
        verify(patientNoteRepository, times(1)).delete(patientNote);
    }

    @Test
    public void testDeletePatientNote_NonExistingNote() {
        // Arrange
        String id = "1";
        when(patientNoteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            patientNoteService.deletePatientNoteById(id);
        });
        verify(patientNoteRepository, times(1)).findById(id);
    }
}
