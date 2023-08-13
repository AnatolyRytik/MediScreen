package com.mediscreen.assessment.dto;

import lombok.Data;

/**
 * DTO class for representing a patient's note.
 */
@Data
public class PatientNoteDto {
    private Long patientId;
    private String note;
}
