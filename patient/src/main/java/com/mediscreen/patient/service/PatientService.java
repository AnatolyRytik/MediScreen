package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDto;

import java.util.List;

public interface PatientService {
    PatientDto createPatient(PatientDto PatientDto);

    PatientDto getPatient(Long id);

    List<PatientDto> getAllPatients();

    PatientDto updatePatient(Long id, PatientDto PatientDto);

    void deletePatient(Long id);
}

