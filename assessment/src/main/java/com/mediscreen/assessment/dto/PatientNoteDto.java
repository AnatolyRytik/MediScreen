package com.mediscreen.assessment.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO class for representing a patient's note.
 */
@Data
public class PatientNoteDto {
    private String id;
    private Long patientId;
    private LocalDate creationDate;
    private String note;
}
