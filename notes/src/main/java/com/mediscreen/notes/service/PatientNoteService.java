package com.mediscreen.notes.service;

import com.mediscreen.notes.model.PatientNote;

import java.util.List;

public interface PatientNoteService {
    PatientNote createPatientNote(PatientNote patientNote);

    PatientNote getPatientNoteById(String id);

    List<PatientNote> getAllPatientNotesByPatientId(Long patientId);

    PatientNote updatePatientNote(String id, PatientNote patientNote);

    void deletePatientNoteById(String id);
}

