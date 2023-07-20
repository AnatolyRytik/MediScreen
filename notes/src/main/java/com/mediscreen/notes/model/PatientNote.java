package com.mediscreen.notes.model;

import com.mediscreen.notes.dto.PatientNoteDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "patientNotes")
public class PatientNote {
    @Id
    private String id;
    private Long patientId;
    private LocalDate creationDate;
    private String note;

    public PatientNote(PatientNoteDto patientNoteDto) {
        this.patientId = patientNoteDto.getPatientId();
        this.note = patientNoteDto.getNote();
    }
}
