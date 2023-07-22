package com.mediscreen.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Patient Notes.
 * This class represents the data to create or update a patient note.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientNoteDto {
    /**
     * The ID of the patient associated with this note.
     */
    private Long patientId;

    /**
     * The content of the patient note.
     */
    private String note;
}
