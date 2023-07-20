package com.mediscreen.notes.service;

import com.mediscreen.notes.model.PatientNote;
import com.mediscreen.notes.repository.PatientNoteRepository;
import com.mediscreen.notes.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the PatientNoteService interface.
 */
@Slf4j
@Service
public class PatientNoteServiceImpl implements PatientNoteService {
    private final PatientNoteRepository patientNoteRepository;

    public PatientNoteServiceImpl(PatientNoteRepository patientNoteRepository) {
        this.patientNoteRepository = patientNoteRepository;
    }

    @Override
    public PatientNote createPatientNote(PatientNote patientNote) {
        log.info("Creating patient note");
        PatientNote savedPatientNote = patientNoteRepository.save(patientNote);
        return savedPatientNote;
    }

    @Override
    public PatientNote getPatientNoteById(String id) {
        log.info("Getting patient note with ID: {}", id);
        return patientNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient note not found with ID: " + id));
    }

    @Override
    public List<PatientNote> getAllPatientNotesByPatientId(Long patientId) {
        log.info("Getting all patient notes for patient with ID: {}", patientId);
        return patientNoteRepository.getAllByPatientId(patientId);
    }

    @Override
    public PatientNote updatePatientNote(String id, PatientNote patientNote) {
        log.info("Updating patient note with ID: {}", id);
        PatientNote existingPatientNote = patientNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient note not found with ID: " + id));

        patientNote.setId(existingPatientNote.getId());

        PatientNote updatedPatientNote = patientNoteRepository.save(patientNote);
        return updatedPatientNote;
    }

    @Override
    public void deletePatientNoteById(String id) {
        log.info("Deleting patient note with ID: {}", id);
        patientNoteRepository.deleteById(id);
    }
}

