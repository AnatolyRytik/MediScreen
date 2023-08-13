package com.mediscreen.patient.proxy;

import com.mediscreen.patient.dto.PatientNoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Proxy interface to interact with the notes-service.
 * This interface provides methods to perform CRUD operations on patient notes using the Feign client.
 */
@FeignClient(name = "notes-service", url = "${notes-service-url}")
public interface NotesProxy {

    /**
     * Fetches a patient note by its unique identifier.
     *
     * @param id The unique identifier of the patient note.
     * @return A {@link PatientNoteDto} containing the details of the patient note.
     */
    @GetMapping("/api/patient-notes/{id}")
    PatientNoteDto getPatientNoteById(@PathVariable("id") String id);

    /**
     * Fetches all patient notes associated with a specific patient.
     *
     * @param patientId The unique identifier of the patient.
     * @return A list of {@link PatientNoteDto} containing details of all notes associated with the given patient.
     */
    @GetMapping("/api/patient-notes/patient/{patientId}")
    List<PatientNoteDto> getAllPatientNotesByPatientId(@PathVariable("patientId") Long patientId);

    /**
     * Creates a new patient note.
     *
     * @param patientNoteDto A {@link PatientNoteDto} containing the details of the note to be created.
     * @return A {@link PatientNoteDto} containing the details of the created note.
     */
    @PostMapping("/api/patient-notes")
    PatientNoteDto createPatientNote(@RequestBody PatientNoteDto patientNoteDto);

    /**
     * Updates an existing patient note.
     *
     * @param id             The unique identifier of the patient note to be updated.
     * @param patientNoteDto A {@link PatientNoteDto} containing the updated details of the note.
     * @return A {@link PatientNoteDto} containing the details of the updated note.
     */
    @PutMapping("/api/patient-notes/{id}")
    PatientNoteDto updatePatientNote(@PathVariable("id") String id, @RequestBody PatientNoteDto patientNoteDto);

    /**
     * Deletes a patient note by its unique identifier.
     *
     * @param id The unique identifier of the patient note to be deleted.
     */
    @DeleteMapping("/api/patient-notes/{id}")
    void deletePatientNoteById(@PathVariable("id") String id);
}