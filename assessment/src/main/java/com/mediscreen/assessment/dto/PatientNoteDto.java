package com.mediscreen.assessment.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientNoteDto {
    private String id;
    private Long patientId;
    private LocalDate creationDate;
    private String note;
}
