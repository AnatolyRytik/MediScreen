package com.mediscreen.notes.service;

import com.mediscreen.notes.dto.PatientNoteDto;
import com.mediscreen.notes.model.PatientNote;
import com.mediscreen.notes.repository.PatientNoteRepository;
import com.mediscreen.notes.util.exception.NotFoundException;
import com.mediscreen.notes.util.mapper.PatientNoteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Implementation of the PatientNoteService interface.
 */
@Slf4j
@Service
public class PatientNoteServiceImpl implements PatientNoteService {
    private final PatientNoteRepository patientNoteRepository;
    private final PatientNoteMapper mapper;

    public PatientNoteServiceImpl(PatientNoteRepository patientNoteRepository, PatientNoteMapper mapper) {
        this.patientNoteRepository = patientNoteRepository;
        this.mapper = mapper;
    }

    @Override
    public PatientNote createPatientNote(PatientNoteDto patientNoteDto) {
        log.info("Creating patient note");

        PatientNote savedPatientNote = mapper.toEntity(patientNoteDto);
        savedPatientNote.setCreationDate(new Date());
        patientNoteRepository.save(savedPatientNote);
        return savedPatientNote;
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
        return patientNoteRepository.getAllByPatientId(patientId);
    }

    @Override
    public PatientNote updatePatientNote(String id, PatientNoteDto patientNoteDto) {
        log.info("Updating patient note with ID: {}", id);
        PatientNote existingPatientNote = patientNoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient note not found with ID: " + id));

        PatientNote patientNote = mapper.toEntity(patientNoteDto);
        patientNote.setId(existingPatientNote.getId());

        return patientNoteRepository.save(patientNote);
    }

    @Override
    public void deletePatientNoteById(String id) {
        log.info("Deleting patient note with ID: {}", id);
        patientNoteRepository.deleteById(id);
    }
}

