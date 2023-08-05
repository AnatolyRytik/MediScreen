package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;

import java.util.List;

/**
 * Service interface for managing patients.
 */
public interface PatientService {
    /**
     * Create a new patient.
     *
     * @param patientDto the patient DTO containing the patient information
     * @return the created patient
     */
    Patient createPatient(PatientDto patientDto);

    /**
     * Get a patient by ID.
     *
     * @param id the ID of the patient
     * @return the patient with the specified ID
     */
    PatientDto getPatient(Long id);

    /**
     * Get all patients.
     *
     * @return a list of all patients
     */
    List<PatientDto> getAllPatients();

    /**
     * Update a patient by ID.
     *
     * @param id         the ID of the patient to update
     * @param patientDto the updated patient DTO
     * @return the updated patient
     */
    Patient updatePatient(Long id, PatientDto patientDto);

    /**
     * Delete a patient by ID.
     *
     * @param id the ID of the patient to delete
     */
    void deletePatient(Long id);
}
