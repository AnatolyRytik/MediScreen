package com.mediscreen.notes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "patientNotes")
public class PatientNote {
    @Id
    private String id;
    private Long patientId;
    private Date creationDate;
    private String note;

    public PatientNote(Long patientId, Date creationDate, String note) {
        this.patientId = patientId;
        this.creationDate = creationDate;
        this.note = note;
    }
}
