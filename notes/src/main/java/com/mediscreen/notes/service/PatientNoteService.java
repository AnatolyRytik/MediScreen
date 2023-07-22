package com.mediscreen.notes.service;

import com.mediscreen.notes.dto.PatientNoteDto;
import com.mediscreen.notes.model.PatientNote;

import java.util.List;

/**
 * Service interface for managing patient notes.
 */
public interface PatientNoteService {
    /**
     * Create a new patient note.
     *
     * @param patientNote the patient note to create
     * @return the created patient note
     */
    PatientNote createPatientNote(PatientNoteDto patientNote);

    /**
     * Get a patient note by ID.
     *
     * @param id the ID of the patient note to retrieve
     * @return the patient note if found
     * @throws com.mediscreen.notes.util.exception.NotFoundException if the patient note is not found
     */
    PatientNote getPatientNoteById(String id);

    /**
     * Get all patient notes for all patients.
     *
     * @return a list of all patient notes
     */
    List<PatientNote> getAllPatientNotes();

    /**
     * Get all patient notes for a specific patient.
     *
     * @param patientId the ID of the patient
     * @return a list of patient notes for the specified patient
     */
    List<PatientNote> getAllPatientNotesByPatientId(Long patientId);

    /**
     * Update a patient note by ID.
     *
     * @param id          the ID of the patient note to update
     * @param patientNote the updated patient note data
     * @return the updated patient note if found
     * @throws com.mediscreen.notes.util.exception.NotFoundException if the patient note is not found
     */
    PatientNote updatePatientNote(String id, PatientNoteDto patientNote);

    /**
     * Delete a patient note by ID.
     *
     * @param id the ID of the patient note to delete
     * @throws com.mediscreen.notes.util.exception.NotFoundException if the patient note is not found
     */
    void deletePatientNoteById(String id);
}
