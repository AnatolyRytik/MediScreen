package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDto;
import com.mediscreen.patient.model.Patient;

import java.util.List;

public interface PatientService {
    Patient createPatient(PatientDto PatientDto);

    PatientDto getPatient(Long id);

    List<Patient> getAllPatients();

    Patient updatePatient(Long id, PatientDto PatientDto);

    void deletePatient(Long id);
}

