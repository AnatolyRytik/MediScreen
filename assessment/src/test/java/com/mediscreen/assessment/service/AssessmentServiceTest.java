package com.mediscreen.assessment.service;

import com.mediscreen.assessment.constants.RiskLevel;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.dto.PatientNoteDto;
import com.mediscreen.assessment.model.Report;
import com.mediscreen.assessment.proxy.PatientNotesProxy;
import com.mediscreen.assessment.proxy.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class AssessmentServiceTest {

    @Mock
    private PatientProxy patientProxy;

    @Mock
    private PatientNotesProxy patientNotesProxy;

    @InjectMocks
    private AssessmentService assessmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatientRiskLevel() {
        // Arrange
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto();
        patientDto.setBirthDate(LocalDate.now().minusYears(35));
        patientDto.setGender("M");
        PatientNoteDto patientNoteDto = new PatientNoteDto();
        patientNoteDto.setNote("Rechute");
        List<PatientNoteDto> patientNoteDtoList = List.of(patientNoteDto);
        when(patientProxy.getPatientById(patientId)).thenReturn(patientDto);
        when(patientNotesProxy.getAllPatientNotesByPatientId(patientId)).thenReturn(patientNoteDtoList);

        // Act
        Report report = assessmentService.getPatientRiskLevel(patientId);

        // Assert
        assertEquals(RiskLevel.NONE, report.getRiskLevel());
        verify(patientProxy, times(1)).getPatientById(patientId);
        verify(patientNotesProxy, times(1)).getAllPatientNotesByPatientId(patientId);
    }

    @Test
    public void testGetPatientRiskLevel_WrongRiskLevel() {
        // Arrange
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto();
        patientDto.setBirthDate(LocalDate.now().minusYears(35));
        patientDto.setGender("M");
        PatientNoteDto patientNoteDto = new PatientNoteDto();
        patientNoteDto.setNote("Rechute");
        List<PatientNoteDto> patientNoteDtoList = List.of(patientNoteDto);
        when(patientProxy.getPatientById(patientId)).thenReturn(patientDto);
        when(patientNotesProxy.getAllPatientNotesByPatientId(patientId)).thenReturn(patientNoteDtoList);

        // Act
        Report report = assessmentService.getPatientRiskLevel(patientId);

        // Assert
        assertNotEquals(RiskLevel.BORDERLINE, report.getRiskLevel());
        verify(patientProxy, times(1)).getPatientById(patientId);
        verify(patientNotesProxy, times(1)).getAllPatientNotesByPatientId(patientId);
    }
}
