package com.mediscreen.patient.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private Long patientId;

    /**
     * The content of the patient note.
     */
    @NotBlank
    private String note;
}
