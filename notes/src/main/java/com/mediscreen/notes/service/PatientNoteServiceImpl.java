package com.mediscreen.notes.service;

import com.mediscreen.notes.dto.PatientNoteDto;
import com.mediscreen.notes.model.PatientNote;
import com.mediscreen.notes.proxy.PatientProxy;
import com.mediscreen.notes.repository.PatientNoteRepository;
import com.mediscreen.notes.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the PatientNoteService interface.
 */
@Slf4j
@Service
public class PatientNoteServiceImpl implements PatientNoteService {
    private final PatientNoteRepository patientNoteRepository;
    private final PatientProxy patientProxy;

    public PatientNoteServiceImpl(PatientNoteRepository patientNoteRepository, PatientProxy patientProxy) {
        this.patientNoteRepository = patientNoteRepository;
        this.patientProxy = patientProxy;
    }

    @Override
    public PatientNote createPatientNote(PatientNoteDto patientNoteDto) {
        log.info("Creating patient note");
        try {
            patientProxy.getPatientById(patientNoteDto.getPatientId());
            PatientNote patientNote = new PatientNote(patientNoteDto);
            patientNote.setCreationDate(LocalDate.now());
            return patientNoteRepository.save(patientNote);
        } catch (NotFoundException e) {
            log.error("Error occurred while creating patient note: Patient not found.", e);
            throw e;
        }
    }

    @Override
    public PatientNote getPatientNoteById(String id) {
        log.info("Getting patient note with ID: {}", id);
        return patientNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient note not found with ID: " + id));
    }

    @Override
    public List<PatientNote> getAllPatientNotes() {
        log.info("Getting all patient notes for all patients");
        return patientNoteRepository.findAll();
    }


    @Override
    public List<PatientNote> getAllPatientNotesByPatientId(Long patientId) {
        log.info("Getting all patient notes for patient with ID: {}", patientId);
        try {
            patientProxy.getPatientById(patientId);
            return patientNoteRepository.getAllByPatientId(patientId);
        } catch (NotFoundException e) {
            log.error("Error occurred while fetching patient notes: Patient not found.", e);
            throw e;
        }
    }

    @Override
    public PatientNote updatePatientNote(String id, PatientNoteDto patientNoteDto) {
        log.info("Updating patient note with ID: {}", id);
        try {
            patientProxy.getPatientById(patientNoteDto.getPatientId());

            PatientNote existingPatientNote = patientNoteRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Patient note not found with ID: " + id));
            PatientNote patientNote = new PatientNote(patientNoteDto);
            patientNote.setId(existingPatientNote.getId());
            patientNote.setCreationDate(existingPatientNote.getCreationDate());
            return patientNoteRepository.save(patientNote);
        } catch (NotFoundException e) {
            log.error("Error occurred while updating patient note: Patient or patient note not found.", e);
            throw e;
        }
    }

    @Override
    public void deletePatientNoteById(String id) {
        log.info("Deleting patient note with ID: {}", id);
        PatientNote existingPatientNote = patientNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient note not found with ID: " + id));
        patientNoteRepository.delete(existingPatientNote);
    }
}

