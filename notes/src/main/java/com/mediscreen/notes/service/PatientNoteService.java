package com.mediscreen.notes.service;

import com.mediscreen.notes.dto.PatientNoteDto;
import com.mediscreen.notes.model.PatientNote;

import java.util.List;

public interface PatientNoteService {
    PatientNote createPatientNote(PatientNoteDto patientNote);

    PatientNote getPatientNoteById(String id);

    List<PatientNote> getAllPatientNotes();

    List<PatientNote> getAllPatientNotesByPatientId(Long patientId);

    PatientNote updatePatientNote(String id, PatientNoteDto patientNote);

    void deletePatientNoteById(String id);
}

