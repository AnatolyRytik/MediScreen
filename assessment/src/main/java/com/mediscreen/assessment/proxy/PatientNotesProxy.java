package com.mediscreen.assessment.proxy;

import com.mediscreen.assessment.dto.PatientNoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Proxy interface for communication with the patient-note-microservice.
 * <p>
 * This interface uses Feign to simplify HTTP communication between microservices.
 */
@FeignClient(name = "patient-note-microservice", url = "localhost:8082")
public interface PatientNotesProxy {

    /**
     * Retrieves a list of patient notes based on the provided patient ID.
     *
     * @param id The ID of the patient.
     * @return A list of {@link PatientNoteDto} objects representing the patient's notes.
     */
    @GetMapping("/api/patient-notes/patient/{id}")
    List<PatientNoteDto> getAllPatientNotesByPatientId(@PathVariable("id") Long id);
}

