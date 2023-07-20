package com.mediscreen.notes.repository;

import com.mediscreen.notes.model.PatientNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientNoteRepository extends MongoRepository<PatientNote, String> {
    List<PatientNote> getAllByPatientId(Long id);
}
