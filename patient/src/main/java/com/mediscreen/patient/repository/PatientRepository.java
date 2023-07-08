package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing patients.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
