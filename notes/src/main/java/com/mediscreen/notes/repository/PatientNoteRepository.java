package com.mediscreen.notes.repository;

import com.mediscreen.notes.model.PatientNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing patient notes.
 */
@Repository
public interface PatientNoteRepository extends MongoRepository<PatientNote, String> {

    /**
     * Get all patient notes for a specific patient.
     *
     * @param id the ID of the patient
     * @return a list of patient notes for the specified patient
     */
    List<PatientNote> getAllByPatientId(Long id);
}
